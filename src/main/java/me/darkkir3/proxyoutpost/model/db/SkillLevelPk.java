package me.darkkir3.proxyoutpost.model.db;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

@Embeddable
public class SkillLevelPk {
    @Column(name="profileUid", nullable = false)
    private Long profileUid;
    @Column(name="agentId", nullable = false)
    private Long agentId;
    @Column(name="skillIndex", nullable = false)
    private int skillIndex;

    public SkillLevelPk() {}

    public SkillLevelPk(Long profileUid, Long agentId, int skillIndex) {
        this.profileUid = profileUid;
        this.agentId = agentId;
        this.skillIndex = skillIndex;
    }

    public Long getProfileUid() {
        return profileUid;
    }

    public Long getAgentId() {
        return agentId;
    }

    public int getSkillIndex() {
        return skillIndex;
    }
}
