package me.darkkir3.proxyoutpost.model.db;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonUnwrapped;
import jakarta.persistence.*;
import me.darkkir3.proxyoutpost.model.enka.SkillLevelList;

@Entity
@Table(name="skill_levels")
public class PlayerSkillLevel implements EnkaToDBMapping<SkillLevelList> {

    /**
     * primary key of skill level
     */
    @EmbeddedId
    private PlayerSkillLevelPk playerSkillLevelPk;

    /**
     * the actual level of the skill
     */
    @Column(name="level")
    private int level;

    /**
     * the playerAgent this skill belongs to
     */
    @JsonIgnore
    @ManyToOne
    @JoinColumn(name="profileUid", referencedColumnName = "profileUid", insertable = false, updatable = false)
    @JoinColumn(name="agentId", referencedColumnName = "agentId", insertable = false, updatable = false)
    private PlayerAgent playerAgent;

    public PlayerSkillLevel() {}

    public PlayerSkillLevel(PlayerSkillLevelPk playerSkillLevelPk) {
        this.playerSkillLevelPk = playerSkillLevelPk;
    }

    @JsonUnwrapped
    public PlayerSkillLevelPk getSkillLevelPk() {
        return playerSkillLevelPk;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    @JsonIgnore
    public PlayerAgent getAgent() {
        return playerAgent;
    }

    @Override
    public void mapEnkaDataToDB(SkillLevelList enkaData) {
        if(this.playerSkillLevelPk != null && enkaData != null) {
            this.level = enkaData.level;
        }
    }
}
