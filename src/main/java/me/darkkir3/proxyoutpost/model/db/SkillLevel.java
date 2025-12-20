package me.darkkir3.proxyoutpost.model.db;

import jakarta.persistence.*;

@Entity
@Table(name="skill_levels")
public class SkillLevel {
    /**
     * primary key of skill level
     */
    @EmbeddedId
    private SkillLevelPk skillLevelPk;
    @Column(name="level")
    private int level;
    @ManyToOne
    @JoinColumns({
            @JoinColumn(name="profileUid", referencedColumnName = "profileUid", insertable = false, updatable = false),
            @JoinColumn(name="agentId", referencedColumnName = "agentId", insertable = false, updatable = false),
    })
    private Agent agent;

    public SkillLevelPk getSkillLevelPk() {
        return skillLevelPk;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }
}
