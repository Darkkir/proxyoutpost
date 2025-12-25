package me.darkkir3.proxyoutpost.utils;

import me.darkkir3.proxyoutpost.configuration.WeaponsConfiguration;
import me.darkkir3.proxyoutpost.model.db.PlayerWeapon;
import me.darkkir3.proxyoutpost.model.output.WeaponOutput;
import me.darkkir3.proxyoutpost.model.output.ItemProperty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import tools.jackson.databind.JsonNode;
import tools.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.Optional;

public final class ItemPropertyTranslator {

    private static final Logger log = LoggerFactory.getLogger(ItemPropertyTranslator.class);
    private static JsonNode levelRootNode = null;
    private static JsonNode starRootNode = null;

    private ItemPropertyTranslator() {}

    public static void translateWeaponProperties(WeaponsConfiguration weaponsConfiguration, PlayerWeapon playerWeapon) {
        if(playerWeapon.getWeaponOutput() == null) {
            log.error("Tried to translate weapon properties for weapon id {}, " +
                            "but no weapon was found in weapons.json",
                    playerWeapon.getWeaponId());
        }

        WeaponOutput weaponOutput = playerWeapon.getWeaponOutput();
        ItemProperty mainStat = weaponOutput.getMainStat();
        ItemProperty secondaryStat = weaponOutput.getSecondaryStat();

        if(mainStat == null || secondaryStat == null) {
            log.error("Weapon with id {} did not specify a main or secondary stat in weapons.json",
                    playerWeapon.getWeaponId());
        }

        ObjectMapper objectMapper = new ObjectMapper();
        try {
            levelRootNode = objectMapper.readTree(new ClassPathResource(weaponsConfiguration.getLevelPath()).getInputStream());
            starRootNode = objectMapper.readTree(new ClassPathResource(weaponsConfiguration.getStarPath()).getInputStream());
        } catch (IOException e) {
            log.error("Failed to read WeaponLevelTemplateTb or WeaponStarTemplateTb from file", e);
        }

        if(levelRootNode == null || starRootNode == null) {
            log.error("Found no valid json to read for WeaponLevelTemplateTb or WeaponStarTemplateTb");
            return;
        }

        JsonNode starWeaponNode = findStarWeaponNode(starRootNode, weaponsConfiguration, weaponOutput.getRarityAsInt(), playerWeapon.getBreakLevel());
        JsonNode levelWeaponNode = findLevelWeaponNode(levelRootNode, weaponsConfiguration, weaponOutput.getRarityAsInt(), playerWeapon.getLevel());

        if(starWeaponNode != null && levelWeaponNode != null) {
            double starMainStat = starWeaponNode.get(weaponsConfiguration.getStarMainStat()).asInt(0);
            double starSecondaryStat = starWeaponNode.get(weaponsConfiguration.getStarSecondaryStat()).asInt(0);

            double levelMainState = levelWeaponNode.get(weaponsConfiguration.getLevelMainStat()).asInt(0);

            double mainStatValue = Math.floor((weaponOutput.getMainStat().propertyValue) * (1d + levelMainState / 10_000d + starMainStat / 10000d));
            double secondaryStatValue = Math.floor((weaponOutput.getSecondaryStat().propertyValue) * (1d + starSecondaryStat / 10_000d));

            //properly translate secondary stat percentages
            secondaryStatValue /= 10_000d;

            playerWeapon.setMainStat(mainStatValue);
            playerWeapon.setSecondaryStat(secondaryStatValue);
        }
    }

    private static JsonNode findStarWeaponNode(JsonNode rootNode, WeaponsConfiguration weaponsConfiguration, int rarity, int breakLevel) {
        if(rootNode != null && !rootNode.isEmpty()) {
            //fetch the first child in the json, it will contain all the child entries
            Optional<JsonNode> arrayRoot = rootNode.values().stream().findFirst();
            if(arrayRoot.isPresent() && arrayRoot.get().isArray()) {
                Optional<JsonNode> weaponNode = arrayRoot.get().valueStream().filter(t -> {
                    return t.get(weaponsConfiguration.getStarRarity()).asInt(-1) == rarity
                            && t.get(weaponsConfiguration.getStarBreakLevel()).asInt(-1) == breakLevel;
                }).findAny();

                if(weaponNode.isEmpty()) {
                    log.error("Could not find weapon data for rarity {} and breakLevel {} in WeaponStarTemplateTb.json",
                            rarity, breakLevel);
                    return null;
                }

                return weaponNode.get();
            }
            else {
                log.error("Failed to parse WeaponStarTemplateTb.json, could not find array root");
            }
        }
        else {
            log.error("Failed to parse WeaponStarTemplateTb.json, file  is empty");
        }

        return null;
    }

    private static JsonNode findLevelWeaponNode(JsonNode rootNode, WeaponsConfiguration weaponsConfiguration, int rarity, int level) {
        if(rootNode != null && !rootNode.isEmpty()) {
            //fetch the first child in the json, it will contain all the child entries
            Optional<JsonNode> arrayRoot = rootNode.values().stream().findFirst();
            if(arrayRoot.isPresent() && arrayRoot.get().isArray()) {
                Optional<JsonNode> weaponNode = arrayRoot.get().valueStream().filter(t -> {
                    return t.get(weaponsConfiguration.getLevelRarity()).asInt(-1) == rarity
                            && t.get(weaponsConfiguration.getLevelLevel()).asInt(-1) == level;
                }).findAny();

                if(weaponNode.isEmpty()) {
                    log.error("Could not find weapon data for rarity {} and level {} in WeaponLevelTemplateTb.json",
                            rarity, level);
                    return null;
                }

                return weaponNode.get();
            }
            else {
                log.error("Failed to parse WeaponLevelTemplateTb.json, could not find array root");
            }
        }
        else {
            log.error("Failed to parse WeaponLevelTemplateTb.json, file  is empty");
        }

        return null;
    }
}
