package me.darkkir3.proxyoutpost.model.db;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class PlayerAgentPk implements Serializable {

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

    public PlayerAgentPk() {}

    public PlayerAgentPk(Long profileUid, Long agentId) {
        this.profileUid = profileUid;
        this.agentId = agentId;
    }

    public Long getProfileUid() {
        return profileUid;
    }

    public Long getAgentId() {
        return agentId;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof PlayerAgentPk that)) return false;
        return Objects.equals(profileUid, that.profileUid) 
                && Objects.equals(agentId, that.agentId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(profileUid, agentId);
    }
}
