package me.darkkir3.proxyoutpost.cache.hakushin;

import me.darkkir3.proxyoutpost.model.hakushin.HakushinAgent;

/**
 * Caches agent related data fetched from hakushin api <br>
 * Hakushin agent data includes:
 * <p>
 * Faction information
 * Core skill bonus stats
 * Core skill and additional information
 * Skill descriptions
 * Mindscape descriptions
 * TODO: Potential + PotentialDetail
 */
public interface HakushinHelper {
    public HakushinAgent fetchHakushinAgentById(String languageToUse, Long agentId);
}
