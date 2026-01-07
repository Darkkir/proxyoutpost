package me.darkkir3.proxyoutpost.cache.hakushin;

import me.darkkir3.proxyoutpost.model.hakushin.HakushinAgent;

/**
 * Caches agent related data fetched from hakushin api <br>
 * Hakushin agent data includes:
 * <p>
 * Faction Name <br>
 * (Camp.XYZ) <br>
 * Faction Icon <br>
 * (https://api.hakush.in/zzz/UI/IconCamp + FactionName.trim() + ".webp") <br>
 * Core Skill Bonus at certain level <br>
 * (ExtraLevel.1-6.Extra for Core A-F) <br>
 * Skill Description: Basic, Dodge, Special, Chain, Assist <br>
 * (Skill.Basic.Description[XYZ] where not exists Skill.Basic.Description[XYZ].Param) <br>
 * Core Passive + Additional Ability <br>
 * (Passive.Level.XYZ.Name + Passive.Level.XYZ.Desc where Passive.Level.XYZ.Level =
 * 1-7 (0 = No core skill, 7 = F) <br>
 * Mindscapes <br>
 * (Talent.1-6.Name and Talent.1-6.Desc and Talent.1-6.Desc2) <br>
 * TODO: Potential + PotentialDetail
 */
public interface HakushinAgentCache {
    public HakushinAgent fetchHakushinAgentById(String languageToUse, Long agentId);
}
