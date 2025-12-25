package me.darkkir3.proxyoutpost.cache;

import me.darkkir3.proxyoutpost.model.output.SuitOutput;

/**
 * Caches agent json data
 */
public interface EnkaSuitCache {

    /**
     * fetch an AgentOutput pojo based on the agent id
     * @param id the id of the agent to fetch
     * @return an AgentOutput instance
     */
    public SuitOutput getSuitById(Long id);
}
