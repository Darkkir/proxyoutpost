package me.darkkir3.proxyoutpost.model.db;

import jakarta.persistence.*;
import me.darkkir3.proxyoutpost.model.enka.SkillLevelList;

@Entity
@Table(name="skill_levels")
public class SkillLevel implements EnkaToDBMapping<SkillLevelList> {

    /**
     * primary key of skill level
     */
    @EmbeddedId
    private SkillLevelPk skillLevelPk;

    /**
     * the actual level of the skill
     */
    @Column(name="level")
    private int level;

    /**
     * the agent this skill belongs to
     */
    @ManyToOne
    @JoinColumns({
            @JoinColumn(name="profileUid", referencedColumnName = "profileUid", insertable = false, updatable = false),
            @JoinColumn(name="agentId", referencedColumnName = "agentId", insertable = false, updatable = false),
    })
    private Agent agent;

    public SkillLevel() {}

    public SkillLevel(SkillLevelPk skillLevelPk) {
        this.skillLevelPk = skillLevelPk;
    }

    public SkillLevelPk getSkillLevelPk() {
        return skillLevelPk;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public Agent getAgent() {
        return agent;
    }

    @Override
    public void mapEnkaDataToDB(SkillLevelList enkaData) {
        if(this.skillLevelPk != null && enkaData != null) {
            this.level = enkaData.level;
        }
    }
}
