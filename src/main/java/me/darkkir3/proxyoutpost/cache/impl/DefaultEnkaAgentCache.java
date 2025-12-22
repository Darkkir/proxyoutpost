package me.darkkir3.proxyoutpost.cache.impl;

import me.darkkir3.proxyoutpost.cache.AbstractEnkaFileCache;
import me.darkkir3.proxyoutpost.cache.EnkaAgentCache;
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


    public DefaultEnkaAgentCache(EnkaAPIConfiguration enkaAPIConfiguration, CacheManager cacheManager) {
        super(enkaAPIConfiguration, cacheManager);
    }

    @Override
    public EnkaStoreType getStoreType() {
        return EnkaStoreType.AVATARS;
    }

    @Override
    public String getCacheName() {
        return AGENT_CACHE;
    }

    @Cacheable(value=AGENT_CACHE)
    @Override
    public AgentOutput getAgentById(Long id) {
        JsonNode rootNode = this.getRootNode();
        if(rootNode != null) {
            JsonNode agentNode = rootNode.get(String.valueOf(id));
            if(agentNode != null) {
                ObjectMapper objectMapper = new ObjectMapper();
                AgentOutput agentOutput = objectMapper.treeToValue(agentNode, AgentOutput.class);
                if(agentOutput != null) {
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
}
