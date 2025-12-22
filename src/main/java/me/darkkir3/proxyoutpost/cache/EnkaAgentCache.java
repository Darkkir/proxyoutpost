package me.darkkir3.proxyoutpost.cache;

import me.darkkir3.proxyoutpost.model.output.AgentOutput;

/**
 * Caches agent json data
 */
public interface EnkaAgentCache {

    /**
     * fetch an AgentOutput pojo based on the agent id
     * @param id the id of the agent to fetch
     * @return an AgentOutput instance
     */
    public AgentOutput getAgentById(Long id);
}
