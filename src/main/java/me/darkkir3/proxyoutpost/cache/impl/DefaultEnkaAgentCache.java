package me.darkkir3.proxyoutpost.cache.impl;

import io.micrometer.common.util.StringUtils;
import me.darkkir3.proxyoutpost.cache.AbstractEnkaFileCache;
import me.darkkir3.proxyoutpost.cache.EnkaAgentCache;
import me.darkkir3.proxyoutpost.cache.EnkaLocalizationCache;
import me.darkkir3.proxyoutpost.cache.EnkaStoreType;
import me.darkkir3.proxyoutpost.configuration.EnkaAPIConfiguration;
import me.darkkir3.proxyoutpost.model.output.AgentOutput;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;
import tools.jackson.databind.JsonNode;
import tools.jackson.databind.ObjectMapper;

@Component
public class DefaultEnkaAgentCache extends AbstractEnkaFileCache implements EnkaAgentCache {

    private static final String AGENT_CACHE = "AGENT_CACHE";
    private static final Logger log = LoggerFactory.getLogger(DefaultEnkaAgentCache.class);

    private final EnkaLocalizationCache enkaLocalizationCache;

    public DefaultEnkaAgentCache(EnkaAPIConfiguration enkaAPIConfiguration, CacheManager cacheManager, EnkaLocalizationCache enkaLocalizationCache) {
        super(enkaAPIConfiguration, cacheManager);
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
