package me.darkkir3.proxyoutpost.cache.impl;

import me.darkkir3.proxyoutpost.cache.*;
import me.darkkir3.proxyoutpost.configuration.EnkaAPIConfiguration;
import me.darkkir3.proxyoutpost.model.db.PlayerAgent;
import me.darkkir3.proxyoutpost.model.output.AgentOutput;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;
import tools.jackson.databind.JsonNode;
import tools.jackson.databind.ObjectMapper;

import java.util.HashMap;
import java.util.Map;

@Component
public class DefaultEnkaAgentCache extends AbstractEnkaFileCache implements EnkaAgentCache {

    private static final String AGENT_CACHE = "AGENT_CACHE";
    private static final Logger log = LoggerFactory.getLogger(DefaultEnkaAgentCache.class);

    private final EnkaPropertyCache enkaPropertyCache;
    private final EnkaLocalizationCache enkaLocalizationCache;

    public DefaultEnkaAgentCache(EnkaAPIConfiguration enkaAPIConfiguration, CacheManager cacheManager, EnkaPropertyCache enkaPropertyCache, EnkaLocalizationCache enkaLocalizationCache) {
        super(enkaAPIConfiguration, cacheManager);
        this.enkaPropertyCache = enkaPropertyCache;
        this.enkaLocalizationCache = enkaLocalizationCache;
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
    @Cacheable(value=AGENT_CACHE)
    @Override
    public AgentOutput getAgentById(String language, Long id) {
        JsonNode rootNode = this.getRootNode();
        if(rootNode != null) {
            JsonNode agentNode = rootNode.get(String.valueOf(id));
            if(agentNode != null) {
                ObjectMapper objectMapper = new ObjectMapper();
                AgentOutput agentOutput = objectMapper.treeToValue(agentNode, AgentOutput.class);
                if(agentOutput != null) {
                    return this.transformAgentFields(agentOutput, id, language);
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
    public void updatePlayerAgentStats(PlayerAgent playerAgent, AgentOutput agentOutput) {
        if(playerAgent != null && agentOutput != null) {
            playerAgent.setAgentOutput(agentOutput);

            //first, list all base stats of this agent
            HashMap<String, Double> basePropertyMap = new HashMap<>();
            if(agentOutput.getBaseProperties() != null) {
                agentOutput.getBaseProperties().forEach((k, v) -> {
                    if(StringUtils.isNumeric(v)) {
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

            playerAgent.setBaseProperties(basePropertyMap);
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
                agentOutput.circleIcon = this.enkaAPIConfiguration.getBaseUrl() + agentOutput.circleIcon;
            }
            if(!StringUtils.isBlank(agentOutput.image)) {
                agentOutput.image = this.enkaAPIConfiguration.getBaseUrl() + agentOutput.image;
            }
        }
        return agentOutput;
    }
}
