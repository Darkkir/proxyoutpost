package me.darkkir3.proxyoutpost.model.db;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

@Embeddable
public class PlayerDriveDiscPk {

    /**
     * the uid of the profile this skill and agent belongs to
     */
    @JsonIgnore
    @Column(name="profileUid", nullable = false)
    private Long profileUid;

    /**
     * the agent id this belongs to
     */
    @JsonIgnore
    @Column(name="agentId", nullable = false)
    private Long agentId;

    /**
     * the slot this disc is equipped on (starting with 1 and ending with 6)
     */
    @Column(name="slot", nullable = false)
    private int slot;

    public PlayerDriveDiscPk() {}

    public PlayerDriveDiscPk(Long profileUid, Long agentId, int slot) {
        this.profileUid = profileUid;
        this.agentId = agentId;
        this.slot = slot;
    }

    public Long getProfileUid() {
        return profileUid;
    }

    public Long getAgentId() {
        return agentId;
    }

    @JsonProperty("Slot")
    public int getSlot() {
        return slot;
    }
}
