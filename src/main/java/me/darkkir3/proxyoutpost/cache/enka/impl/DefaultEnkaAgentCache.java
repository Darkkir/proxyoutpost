package me.darkkir3.proxyoutpost.cache.enka.impl;

import me.darkkir3.proxyoutpost.cache.enka.*;
import me.darkkir3.proxyoutpost.cache.hakushin.HakushinAgentCache;
import me.darkkir3.proxyoutpost.configuration.EnkaAPIConfiguration;
import me.darkkir3.proxyoutpost.equipment.ItemPropertyTranslator;
import me.darkkir3.proxyoutpost.model.db.PlayerAgent;
import me.darkkir3.proxyoutpost.model.db.PlayerAgentProperty;
import me.darkkir3.proxyoutpost.model.db.PlayerAgentPropertyPk;
import me.darkkir3.proxyoutpost.model.db.PlayerDriveDiscProperty;
import me.darkkir3.proxyoutpost.model.hakushin.HakushinAgent;
import me.darkkir3.proxyoutpost.model.output.AgentOutput;
import me.darkkir3.proxyoutpost.utils.transformer.ImageUrlTransformer;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;
import tools.jackson.databind.JsonNode;
import tools.jackson.databind.ObjectMapper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class DefaultEnkaAgentCache extends AbstractEnkaFileCache implements EnkaAgentCache {

    private static final String AGENT_CACHE = "ENKA_AGENT_CACHE";
    private static final Logger log = LoggerFactory.getLogger(DefaultEnkaAgentCache.class);

    private final EnkaPropertyCache enkaPropertyCache;
    private final ItemPropertyTranslator itemPropertyTranslator;
    private final ImageUrlTransformer imageUrlTransformer;
    private final EnkaLocalizationCache enkaLocalizationCache;
    private final HakushinAgentCache hakushinAgentCache;

    public DefaultEnkaAgentCache(EnkaAPIConfiguration enkaAPIConfiguration, CacheManager cacheManager, EnkaPropertyCache enkaPropertyCache, ItemPropertyTranslator itemPropertyTranslator, ImageUrlTransformer imageUrlTransformer, EnkaLocalizationCache enkaLocalizationCache, HakushinAgentCache hakushinAgentCache) {
        super(enkaAPIConfiguration, cacheManager);
        this.enkaPropertyCache = enkaPropertyCache;
        this.itemPropertyTranslator = itemPropertyTranslator;
        this.imageUrlTransformer = imageUrlTransformer;
        this.enkaLocalizationCache = enkaLocalizationCache;
        this.hakushinAgentCache = hakushinAgentCache;
    }

    @Override
    public EnkaStoreType getStoreType() {
        return EnkaStoreType.AVATARS;
    }

    @Override
    public String getCacheName() {
        return AGENT_CACHE;
    }

    /**
     * grab the agent by id while trying to translate fields like the name
     * @param id the id of the agent to fetch
     * @return the translated agent instance
     */
    @Cacheable(value = AGENT_CACHE)
    @Override
    public AgentOutput getAgentById(String language, Long id) {
        JsonNode rootNode = this.getRootNode();
        if(rootNode != null) {
            JsonNode agentNode = rootNode.get(String.valueOf(id));
            if(agentNode != null) {
                ObjectMapper objectMapper = new ObjectMapper();
                AgentOutput agentOutput = objectMapper.treeToValue(agentNode, AgentOutput.class);
                if(agentOutput != null) {
                    this.transformAgentFields(agentOutput, id, language);
                    HakushinAgent hakushinAgent = hakushinAgentCache.fetchHakushinAgentById(language, id);
                    agentOutput.setHakushinAgent(hakushinAgent);
                    return agentOutput;
                }
                else {
                    log.error("Failed to parse {} for agent id {}", this.getStoreName(), id);
                }
            }
            else {
                log.error("Could not find agent with id {}", id);

            }
        }

        return null;
    }

    @Override
    public void updatePlayerAgentStats(String language, PlayerAgent playerAgent, AgentOutput agentOutput) {
        if(playerAgent != null && agentOutput != null) {
            playerAgent.setAgentOutput(agentOutput);

            //first, list all base stats of this agent
            HashMap<String, Double> basePropertyMap = new HashMap<>();
            if(agentOutput.getBaseProperties() != null) {
                agentOutput.getBaseProperties().forEach((k, v) -> {
                    if(StringUtils.isNumeric(k) && StringUtils.isNumeric(v)) {
                        basePropertyMap.put(k, Double.parseDouble(v));
                    }
                });
            }

            //increase those stats by growth properties based on the agent level
            if(agentOutput.getGrowthProperties() != null) {
                basePropertyMap.forEach((k, v) -> {
                    String growthValue = agentOutput.getGrowthProperties().get(k);
                    if(StringUtils.isNumeric(growthValue)) {
                        v += (Double.parseDouble(growthValue) * ((double)playerAgent.getAgentLevel() - 1)) / 10_000d;
                        basePropertyMap.put(k, Math.floor(v));
                    }
                });
            }

            //increase those stats by promotion properties based on the promotion level of the agent
            if(agentOutput.getPromotionProperties() != null) {
                Map<String, String> promotionProperties =
                        agentOutput.getPromotionProperties().get(playerAgent.getAgentPromotionLevel() - 1);
                if(promotionProperties != null) {
                    basePropertyMap.forEach((k, v) -> {
                        String promotionValue = promotionProperties.get(k);
                        if(StringUtils.isNumeric(promotionValue)) {
                            v += Double.parseDouble(promotionValue);
                            basePropertyMap.put(k, Math.floor(v));
                        }
                    });
                }
            }

            //finally, increase those stats by the core enhancement properties based on the level of the core skill
            if(agentOutput.getCoreEnhancementProperties() != null) {
                Map<String, String> coreEnhancementProperties =
                        agentOutput.getCoreEnhancementProperties().get(playerAgent.getCoreSkillEnhancement());
                if(coreEnhancementProperties != null) {
                    basePropertyMap.forEach((k, v) -> {
                        String coreEnhancementValue = coreEnhancementProperties.get(k);
                        if(StringUtils.isNumeric(coreEnhancementValue)) {
                            v += Double.parseDouble(coreEnhancementValue);
                            basePropertyMap.put(k, Math.floor(v));
                        }
                    });
                }
            }

            //then set the values to the actual player agent instance
            Map<Long, PlayerAgentProperty> existingPropertyList = playerAgent.getPropertyMap();
            if(existingPropertyList == null) {
                existingPropertyList = new HashMap<>();
            }

            final List<PlayerAgentProperty> newProperties = new ArrayList<>();
            basePropertyMap.forEach((k, v) -> {
                PlayerAgentProperty playerAgentProperty = new PlayerAgentProperty(
                        new PlayerAgentPropertyPk(
                                playerAgent.getAgentPk().getProfileUid(),
                                playerAgent.getAgentPk().getAgentId(),
                                Long.parseLong(k)));

                playerAgentProperty.setBaseValue((int)Math.floor(v));
                newProperties.add(playerAgentProperty);
            });

            //insert or update existing values
            for(PlayerAgentProperty p : newProperties) {
                long key = p.getPlayerAgentPropertyPk().getPropertyId();
                PlayerAgentProperty existingProperty = existingPropertyList.get(key);
                if(existingProperty != null) {
                    existingProperty.setBaseValue(p.getBaseValue());
                }
                else {
                    existingPropertyList.put(key, p);
                }
            }

            //always include certain properties in the output
            if(enkaAPIConfiguration.getPropertiesToInclude() != null) {
                Map<Long, PlayerAgentProperty> finalExistingPropertyList = existingPropertyList;
                enkaAPIConfiguration.getPropertiesToInclude().forEach(t -> {
                    PlayerAgentProperty p = finalExistingPropertyList.get(t);
                    if(p == null) {
                        p = new PlayerAgentProperty(new PlayerAgentPropertyPk(playerAgent.getAgentPk().getProfileUid(),
                                playerAgent.getAgentPk().getAgentId(), t));
                        p.setPropertyOutput(this.enkaPropertyCache.getPropertyById(language, t));
                        finalExistingPropertyList.put(t, p);
                    }
                });
            }

            playerAgent.setPropertyMap(existingPropertyList);

            //set property translations from cache for drive disc properties too
            if(playerAgent.getPlayerDriveDiscs() != null && !playerAgent.getPlayerDriveDiscs().isEmpty()) {
                playerAgent.getPlayerDriveDiscs().forEach(t -> {
                    itemPropertyTranslator.transformDriveDiscStats(t);

                    if(t.getSecondaryProperties() != null && !t.getSecondaryProperties().isEmpty()) {
                        t.getSecondaryProperties().forEach((p ->
                            p.setPropertyOutput(this.enkaPropertyCache.getPropertyById(language,
                                    p.getPlayerDriveDiscPropertyPk().getPropertyId()))));
                    }

                    PlayerDriveDiscProperty mainProperty = t.getMainProperty();
                    if(mainProperty != null) {
                        mainProperty.setPropertyOutput(this.enkaPropertyCache.getPropertyById(language, mainProperty.getPlayerDriveDiscPropertyPk().getPropertyId()));
                    }
                });
            }

            //calculate total stats of this agent
            itemPropertyTranslator.updatePlayerAgentTotalStats(language, playerAgent);
        }
    }

    /**
     * modifies the passed agent pojo by translating fields
     * and setting ids
     * @param agentOutput the agent to transform
     * @return the transformed agent instance
     */
    private AgentOutput transformAgentFields(AgentOutput agentOutput, Long id, String language) {
        if(agentOutput != null) {
            //translate the name
            String internalAgentName = agentOutput.name;
            agentOutput.name = this.enkaLocalizationCache.translate(language, internalAgentName);
            //set the agent id
            agentOutput.agentId = id;
            //prefix all image URLs with the base URL of enka
            if(!StringUtils.isBlank(agentOutput.circleIcon)) {
                agentOutput.circleIcon =
                        this.imageUrlTransformer.transformAgentCircleIcon(
                                id,
                                this.enkaAPIConfiguration.getBaseUrl() + agentOutput.circleIcon);
            }
            if(!StringUtils.isBlank(agentOutput.image)) {
                agentOutput.image =
                        this.imageUrlTransformer.transformAgentImage(
                                id,
                                this.enkaAPIConfiguration.getBaseUrl() + agentOutput.image);
            }
        }
        return agentOutput;
    }
}
