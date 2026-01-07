package me.darkkir3.proxyoutpost.model.hakushin;

import me.darkkir3.proxyoutpost.model.db.SkillType;
import me.darkkir3.proxyoutpost.model.output.ItemProperty;
import tools.jackson.databind.annotation.JsonDeserialize;

import java.util.List;
import java.util.Map;

@JsonDeserialize(using = HakushinAgentDeserializer.class)
public class HakushinAgent {

    /**
     * the faction name, e.g. 'Spook Shack'
     */
    private String factionName;

    /**
     * url to the faction image, e.g. https://api.hakush.in/zzz/UI/IconCampSpookShack.webp
     */
    private String factionIcon;

    /**
     * Core skill bonus properties in the range of level 1 (core skill A learned) to level 6 (core skill F learned)
     */
    private Map<Integer, List<ItemProperty>> coreSkillBonus;

    /**
     * Core skill + additional descriptions in the range of level 1 (no core skill learned) <br>
     * to level 7 (core skill F learned)
     */
    private Map<Integer, HakushinCoreSkill> coreSkillDescriptions;

    /**
     * Skill descriptions at max level
     */
    private Map<SkillType, List<HakushinAgentSkill>> skillDescriptions;

    public HakushinAgent() {}

    public HakushinAgent(String factionName,
                         String factionIcon,
                         Map<Integer, List<ItemProperty>> coreSkillBonus,
                         Map<Integer, HakushinCoreSkill> coreSkillDescriptions,
                         Map<SkillType,
                                 List<HakushinAgentSkill>> skillDescriptions) {
        this.factionName = factionName;
        this.factionIcon = factionIcon;
        this.coreSkillBonus = coreSkillBonus;
        this.coreSkillDescriptions = coreSkillDescriptions;
        this.skillDescriptions = skillDescriptions;
    }

    public String getFactionName() {
        return factionName;
    }

    public void setFactionName(String factionName) {
        this.factionName = factionName;
    }

    public String getFactionIcon() {
        return factionIcon;
    }

    public void setFactionIcon(String factionIcon) {
        this.factionIcon = factionIcon;
    }

    public Map<Integer, List<ItemProperty>> getCoreSkillBonus() {
        return coreSkillBonus;
    }

    public void setCoreSkillBonus(Map<Integer, List<ItemProperty>> coreSkillBonus) {
        this.coreSkillBonus = coreSkillBonus;
    }

    public Map<Integer, HakushinCoreSkill> getCoreSkillDescriptions() {
        return coreSkillDescriptions;
    }

    public void setCoreSkillDescriptions(Map<Integer, HakushinCoreSkill> coreSkillDescriptions) {
        this.coreSkillDescriptions = coreSkillDescriptions;
    }

    public Map<SkillType, List<HakushinAgentSkill>> getSkillDescriptions() {
        return skillDescriptions;
    }

    public void setSkillDescriptions(Map<SkillType, List<HakushinAgentSkill>> skillDescriptions) {
        this.skillDescriptions = skillDescriptions;
    }
}
