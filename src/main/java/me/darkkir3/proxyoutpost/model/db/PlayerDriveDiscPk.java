package me.darkkir3.proxyoutpost.model.db;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.util.Objects;

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

    @JsonProperty("slot")
    public int getSlot() {
        return slot;
    }

    public void setProfileUid(Long profileUid) {
        this.profileUid = profileUid;
    }

    public void setAgentId(Long agentId) {
        this.agentId = agentId;
    }

    public void setSlot(int slot) {
        this.slot = slot;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof PlayerDriveDiscPk that)) return false;
        return slot == that.slot
                && Objects.equals(profileUid, that.profileUid)
                && Objects.equals(agentId, that.agentId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(profileUid, agentId, slot);
    }
}
