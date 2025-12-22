package me.darkkir3.proxyoutpost.cache.impl;

import me.darkkir3.proxyoutpost.cache.AbstractEnkaFileCache;
import me.darkkir3.proxyoutpost.cache.EnkaAgentCache;
import me.darkkir3.proxyoutpost.cache.EnkaStoreType;
import me.darkkir3.proxyoutpost.configuration.EnkaAPIConfiguration;
import me.darkkir3.proxyoutpost.model.output.AgentOutput;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.Cacheable;

public class DefaultEnkaAgentCache extends AbstractEnkaFileCache implements EnkaAgentCache {

    private static final String AGENT_CACHE = "AGENT_CACHE";

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
    public AgentOutput getAgentById(String id) {
        return null;
    }
}
