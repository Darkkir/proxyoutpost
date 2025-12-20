package me.darkkir3.proxyoutpost.model.db;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.io.Serializable;

@Embeddable
public class AgentPk implements Serializable {

    /**
     * the uid of the profile this belongs to
     */
    @Column(name="profileUid", nullable = false)
    private Long profileUid;

    /**
     * agent id in avatars.json
     */
    @Column(name="agentId", nullable = false)
    private Long agentId;

    public AgentPk() {}

    public AgentPk(Long profileUid, Long agentId) {
        this.profileUid = profileUid;
        this.agentId = agentId;
    }

    public Long getProfileUid() {
        return profileUid;
    }

    public Long getAgentId() {
        return agentId;
    }
}
