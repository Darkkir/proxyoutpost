package me.darkkir3.proxyoutpost.equipment;

import me.darkkir3.proxyoutpost.cache.EnkaLocalizationCache;
import me.darkkir3.proxyoutpost.cache.EnkaPropertyCache;
import me.darkkir3.proxyoutpost.cache.EnkaSuitCache;
import me.darkkir3.proxyoutpost.configuration.EnkaAPIConfiguration;
import me.darkkir3.proxyoutpost.configuration.FileCfgConfiguration;
import me.darkkir3.proxyoutpost.model.db.*;
import me.darkkir3.proxyoutpost.model.output.ItemProperty;
import me.darkkir3.proxyoutpost.model.output.WeaponOutput;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import tools.jackson.databind.JsonNode;
import tools.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * util class that helps with handling the XYZTemplate.json files from the game data
 * and also does some basic calculations
 */
@Component
public class ItemPropertyTranslator {

    private static final Logger log = LoggerFactory.getLogger(ItemPropertyTranslator.class);

    private final EnkaAPIConfiguration enkaAPIConfiguration;
    private final FileCfgConfiguration fileCfgConfiguration;
    private final EnkaLocalizationCache enkaLocalizationCache;
    private final EnkaSuitCache enkaSuitCache;
    private final EnkaPropertyCache enkaPropertyCache;

    private JsonNode levelRootNode = null;
    private JsonNode starRootNode = null;
    private JsonNode equipmentRootNode = null;

    private DriveDiscLibrary driveDiscLibrary;

    /**
     * map that stores file names and the timestamps
     * of when we last fetched the file from url
     */
    private final HashMap<String, LocalDateTime> lastFetchTimes;

    public ItemPropertyTranslator(EnkaAPIConfiguration enkaAPIConfiguration, FileCfgConfiguration fileCfgConfiguration, EnkaLocalizationCache enkaLocalizationCache, EnkaSuitCache enkaSuitCache, EnkaPropertyCache enkaPropertyCache) {
        this.enkaAPIConfiguration = enkaAPIConfiguration;
        this.fileCfgConfiguration = fileCfgConfiguration;
        this.enkaLocalizationCache = enkaLocalizationCache;
        this.enkaSuitCache = enkaSuitCache;
        this.enkaPropertyCache = enkaPropertyCache;

        this.lastFetchTimes = new HashMap<>();
    }

    /**
     * Update the drive discs of this agent by transforming the main stat
     * and finding the proper suit (drive disc set) for that disc
     * @param playerDriveDisc the drive disc to transform
     */
    public void transformDriveDiscStats(PlayerDriveDisc playerDriveDisc) {
        if (playerDriveDisc == null || playerDriveDisc.getSetId() == 0) {
            log.error("Tried to translate a drive disc without a set id, this shouldn't happen");
            return;
        }

        if(!StringUtils.isBlank(playerDriveDisc.getSetName())) {
            //looks like this disc already is transformed
            return;
        }

        PlayerDriveDiscProperty mainStatProperty = playerDriveDisc.getMainProperty();
        if (mainStatProperty == null || mainStatProperty.getPropertyValue() == 0) {
            log.error("Tried to translate a drive disc without properties, this shouldn't happen");
            return;
        }

        ObjectMapper objectMapper = new ObjectMapper();
        equipmentRootNode = this.readFileConfiguration(
                fileCfgConfiguration.getEquipmentPath(),
                fileCfgConfiguration.getEquipmentFileName(),
                fileCfgConfiguration.getEquipmentUrl(),
                objectMapper,
                equipmentRootNode);

        JsonNode newDriveDiscRootNode = this.readFileConfiguration(
                fileCfgConfiguration.getDriveDiscPath(),
                fileCfgConfiguration.getDriveDiscFileName(),
                fileCfgConfiguration.getDriveDiscUrl(),
                objectMapper,
                null);

        //if we refreshed the library config
        if (newDriveDiscRootNode != null) {
            this.driveDiscLibrary = objectMapper.treeToValue(newDriveDiscRootNode, DriveDiscLibrary.class);
            this.enkaSuitCache.clearCache();
        }

        Long suitId = 0L;
        DriveDiscSuit suitToUse = null;
        if (this.driveDiscLibrary != null && this.driveDiscLibrary.driveDiscRarityMap != null) {
            DriveDiscRarity rarity = this.driveDiscLibrary.driveDiscRarityMap.get(playerDriveDisc.getSetId());
            if(rarity != null) {
                playerDriveDisc.setRarity(rarity.rarity);

                if(this.driveDiscLibrary.driveDiscSuitMap != null) {
                    suitId = rarity.suitId;
                    suitToUse = this.driveDiscLibrary.driveDiscSuitMap.get(suitId);
                }
            }
            else {
                log.error("Could not find drive disc rarity for set id {} in {}",
                        playerDriveDisc.getSetId(), fileCfgConfiguration.getDriveDiscFileName());
                return;
            }
        }

        if(suitToUse != null) {
            this.enkaSuitCache.registerDriveDiscSuit(suitToUse);
        }
        else {
            log.error("Failed to find suit {} in {}",
                    suitId, fileCfgConfiguration.getDriveDiscFileName());
        }

        //check if the main stat already has been calculated by
        // checking whether we already set a suit name
        if(StringUtils.isBlank(playerDriveDisc.getSetName())) {
            JsonNode equipmentLevelNode = this.findItemNode(
                    equipmentRootNode,
                    fileCfgConfiguration.getEquipmentRarity(),
                    fileCfgConfiguration.getEquipmentLevel(),
                    playerDriveDisc.getRarityAsInt(),
                    playerDriveDisc.getLevel(),
                    fileCfgConfiguration.getEquipmentFileName());

            if(equipmentLevelNode != null) {
                double propertyValue = mainStatProperty.getPropertyValue();
                double equipmentValue = equipmentLevelNode.get(fileCfgConfiguration.getEquipmentMainStat()).asInt(0);
                propertyValue = Math.floor(propertyValue * (1d + equipmentValue / 10_000d));
                mainStatProperty.setPropertyValue((int)propertyValue);

                //update suit name after we calculated the main stat properly
                if(suitToUse != null) {
                    playerDriveDisc.setSetName(suitToUse.getName());
                }
            }
        }
    }

    /**
     * update the total stats of this agent by applying drive disc properties
     * (including set bonus and secondary stats)
     * @param playerAgent the agent to update
     */
    public void updatePlayerAgentTotalStats(String language, PlayerAgent playerAgent) {
        Assert.notNull(playerAgent, "playerAgent is null");

        if(playerAgent.getPlayerDriveDiscs() == null) {
            log.error("Tried to calculate stats for agent {} of profile {}, " +
                    "but no equipped drive discs were found",
                    playerAgent.getAgentPk().getAgentId(),
                    playerAgent.getAgentPk().getProfileUid());
        }

        if(playerAgent.getPropertyMap() == null) {
            log.error("Tried to calculate stats for agent {} of profile {}, " +
                            "but no properties were found",
                    playerAgent.getAgentPk().getAgentId(),
                    playerAgent.getAgentPk().getProfileUid());
        }

        List<PlayerDriveDisc> driveDiscs = playerAgent.getPlayerDriveDiscs();
        Map<Long, PlayerAgentProperty> propertyMap = playerAgent.getPropertyMap();

        //count all drive discs with the same suit id
        Map<String, Integer> suitCount = new HashMap<>();
        driveDiscs.forEach(t -> {
            if(!StringUtils.isBlank(t.getSetName())) {
                int currentCount = suitCount.getOrDefault(t.getSetName(), 0);
                suitCount.put(t.getSetName(), ++currentCount);
            }
        });

        int duplicatesNeededForSetBonus = fileCfgConfiguration.getDriveDiscDiscsPerSet();

        //now, calculate total stats
        Map<Long, Integer> totalStats = new HashMap<>();
        Map<Long, Integer> totalRolls = new HashMap<>();

        //first, add base stats from the agent
        propertyMap.forEach((key, value) ->
                totalStats.put(key, value.getBaseValue()));

        if(playerAgent.getWeapon() != null) {
            PlayerWeapon playerWeapon = playerAgent.getWeapon();
            double mainStat = playerWeapon.getMainStatAsDouble();
            double secondaryStat = playerWeapon.getSecondaryStatAsDouble();

            if(mainStat > 0d || secondaryStat > 0d) {
                long mainStatPropertyId = playerWeapon.getWeaponOutput().getMainStat().propertyId;
                long secondaryStatPropertyId = playerWeapon.getWeaponOutput().getSecondaryStat().propertyId;
                totalStats.put(mainStatPropertyId, totalStats.getOrDefault(mainStatPropertyId, 0) + (int)mainStat);
                totalStats.put(secondaryStatPropertyId, totalStats.getOrDefault(secondaryStatPropertyId, 0) + (int)secondaryStat);
            }
        }

        //then, add the set bonus values
        for(Map.Entry<String, Integer> e : suitCount.entrySet()) {
            if(e.getValue() >= duplicatesNeededForSetBonus) {
                //apply the set bonus for that set
                DriveDiscSuit suitToApply = this.enkaSuitCache.getSuitByName(enkaLocalizationCache.getDefaultLanguage(), e.getKey());
                if(suitToApply != null) {
                    Map<Long, Integer> setBonusMap = suitToApply.getSetBonusProps();
                    for(Map.Entry<Long, Integer> s : setBonusMap.entrySet()) {
                        //increment the stat by the bonus of that suit
                        totalStats.put(
                                s.getKey(), totalStats.getOrDefault(s.getKey(), 0) + s.getValue());
                    }
                } else {
                    log.error("Failed to find cached suit by id {}", e.getKey());
                }
            }
        }

        //then apply drive discs stats
        driveDiscs.forEach(t -> {
            PlayerDriveDiscProperty mainProperty = t.getMainProperty();
            //apply main stat value
            this.incrementTotalStatsForProperty(mainProperty, totalStats);
            List<PlayerDriveDiscProperty> secondaryProperties = t.getSecondaryProperties();

            if(secondaryProperties != null) {
                secondaryProperties.forEach(p -> {
                    //apply secondary stats
                   this.incrementTotalStatsForProperty(p, totalStats);
                   if(p != null && p.getPlayerDriveDiscPropertyPk() != null) {
                       long propertyId = p.getPlayerDriveDiscPropertyPk().getPropertyId();
                       int rolls = p.getPropertyLevel();
                       //increment roll number of property for secondary stats
                       totalRolls.put(propertyId, totalRolls.getOrDefault(propertyId, 0) + rolls);
                   }
                });
            }
        });

        totalStats.forEach((key, value) -> {
            PlayerAgentProperty existingProperty = propertyMap.get(key);
            //if this is not a base property, add it to the list of properties
            if (existingProperty == null) {
                existingProperty = new PlayerAgentProperty(
                        new PlayerAgentPropertyPk(
                                playerAgent.getAgentPk().getProfileUid(),
                                playerAgent.getAgentPk().getAgentId(),
                                key));

                existingProperty.setBaseValue(0);
            }

            existingProperty.setTotalValue(value);
            existingProperty.setTotalRolls(totalRolls.getOrDefault(key, 0));
            existingProperty.setPropertyOutput(enkaPropertyCache.getPropertyById(language, key));
            propertyMap.put(key, existingProperty);
        });
        playerAgent.setPropertyMap(propertyMap);
    }

    private void incrementTotalStatsForProperty(PlayerDriveDiscProperty driveDiscProperty, Map<Long, Integer> totalStats) {
        if(driveDiscProperty != null && driveDiscProperty.getPlayerDriveDiscPropertyPk() != null) {
            long propertyId = driveDiscProperty.getPlayerDriveDiscPropertyPk().getPropertyId();
            int propertyLevel = driveDiscProperty.getPropertyLevel();
            int propertyValue = driveDiscProperty.getPropertyValue();

            totalStats.put(propertyId, totalStats.getOrDefault(propertyId, 0) + (propertyValue * propertyLevel));
        }
    }

    public void translateWeaponProperties(PlayerWeapon playerWeapon) {
        if (playerWeapon.getWeaponOutput() == null) {
            log.error("Tried to translate weapon properties for weapon id {}, " +
                            "but no weapon was found in weapons.json",
                    playerWeapon.getWeaponId());
        }

        WeaponOutput weaponOutput = playerWeapon.getWeaponOutput();
        ItemProperty mainStat = weaponOutput.getMainStat();
        ItemProperty secondaryStat = weaponOutput.getSecondaryStat();

        if (mainStat == null || secondaryStat == null) {
            log.error("Weapon with id {} did not specify a main or secondary stat in weapons.json",
                    playerWeapon.getWeaponId());
        }

        ObjectMapper objectMapper = new ObjectMapper();


        levelRootNode = this.readFileConfiguration(
                fileCfgConfiguration.getLevelPath(),
                fileCfgConfiguration.getLevelFileName(),
                fileCfgConfiguration.getLevelUrl(),
                objectMapper,
                levelRootNode);


        starRootNode = this.readFileConfiguration(
                fileCfgConfiguration.getStarPath(),
                fileCfgConfiguration.getStarFileName(),
                fileCfgConfiguration.getStarUrl(),
                objectMapper,
                starRootNode);

        if (levelRootNode == null || starRootNode == null) {
            log.error("Found no valid json to read for WeaponLevelTemplateTb or WeaponStarTemplateTb");
            return;
        }

        JsonNode starWeaponNode = this.findItemNode(
                starRootNode,
                fileCfgConfiguration.getStarRarity(),
                fileCfgConfiguration.getStarBreakLevel(),
                weaponOutput.getRarityAsInt(),
                playerWeapon.getBreakLevel(),
                fileCfgConfiguration.getStarFileName());

        JsonNode levelWeaponNode = this.findItemNode(
                levelRootNode,
                fileCfgConfiguration.getLevelRarity(),
                fileCfgConfiguration.getLevelLevel(),
                weaponOutput.getRarityAsInt(),
                playerWeapon.getLevel(),
                fileCfgConfiguration.getLevelFileName());

        if (starWeaponNode != null && levelWeaponNode != null) {
            double starMainStat = starWeaponNode.get(fileCfgConfiguration.getStarMainStat()).asInt(0);
            double starSecondaryStat = starWeaponNode.get(fileCfgConfiguration.getStarSecondaryStat()).asInt(0);

            double levelMainState = levelWeaponNode.get(fileCfgConfiguration.getLevelMainStat()).asInt(0);

            double mainStatValue = Math.floor((weaponOutput.getMainStat().propertyValue) * (1d + levelMainState / 10_000d + starMainStat / 10000d));
            double secondaryStatValue = Math.floor((weaponOutput.getSecondaryStat().propertyValue) * (1d + starSecondaryStat / 10_000d));

            playerWeapon.setMainStat(mainStatValue);
            playerWeapon.setSecondaryStat(secondaryStatValue);
        }
    }

    private JsonNode findItemNode(JsonNode rootNode, String firstField, String secondField, int firstValue, int secondValue, String fileName) {
        if (rootNode != null && !rootNode.isEmpty()) {
            //fetch the first child in the json, it will contain all the child entries
            Optional<JsonNode> arrayRoot = rootNode.values().stream().findFirst();
            if (arrayRoot.isPresent() && arrayRoot.get().isArray()) {
                Optional<JsonNode> weaponNode = arrayRoot.get().valueStream().filter(t -> {
                    return t.get(firstField).asInt(-1) == firstValue
                            && t.get(secondField).asInt(-1) == secondValue;
                }).findAny();

                if (weaponNode.isEmpty()) {
                    log.error("Could not find item data with {} = {} and {} = {} in {}",
                            firstField, firstValue, secondField, secondValue, fileName);
                    return null;
                }

                return weaponNode.get();
            } else {
                log.error("Failed to parse {}, could not find array root", fileName);
            }
        } else {
            log.error("Failed to parse {}, file  is empty", fileName);
        }

        return null;
    }

    /**
     * attempt to read the passed file from classpath,
     * download it otherwise
     *
     * @param path         the path of the file to look for
     * @param sourceUrl    the url to download from
     * @param objectMapper the object mapper to read the file with
     * @param current      the current tree root node of this file
     * @return an JsonNode instance (or current if we do not need to refresh)
     */
    private JsonNode readFileConfiguration(String path, String fileName, String sourceUrl, ObjectMapper objectMapper, JsonNode current) {

        File targetFile = new File(path, fileName);
        //create the configuration folder if needed
        if (!targetFile.getParentFile().exists() && targetFile.getParentFile().mkdirs()) {
            log.info("Created config folder for {}", fileName);
        }

        LocalDateTime currentTime = LocalDateTime.now();
        LocalDateTime lastFetchTime = lastFetchTimes.get(fileName);
        if (lastFetchTime == null) {
            lastFetchTime = LocalDateTime.MIN;
        }

        if (!targetFile.exists() || currentTime.isAfter(lastFetchTime.plusHours(enkaAPIConfiguration.getRefreshTimeInHours()))) {
            //try to download it from sourceUrl
            log.info("Saving fileCfg file {} to {}", sourceUrl, targetFile.getPath());
            try (FileOutputStream fos = new FileOutputStream(targetFile);
                 ReadableByteChannel rbc = Channels.newChannel(new URI(sourceUrl).toURL().openStream())) {
                fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
            } catch (IOException | URISyntaxException e) {
                log.error("Failed to read and save fileCfg file {} from url {}",
                        fileName, sourceUrl, e);
            } finally {
                lastFetchTimes.put(fileName, currentTime);
                current = objectMapper.readTree(targetFile);
            }
        }

        return current;
    }
}
