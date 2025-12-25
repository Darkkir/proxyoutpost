package me.darkkir3.proxyoutpost.cache;

import me.darkkir3.proxyoutpost.model.db.PlayerAgent;
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
    public AgentOutput getAgentById(String language, Long id);

    /**
     * Update the agent stats based on the generic data gathered in avatars.json
     * @param playerAgent the actual player agent
     * @param agentOutput the generic agent information
     */
    public void updatePlayerAgentStats(PlayerAgent playerAgent, AgentOutput agentOutput);
}
