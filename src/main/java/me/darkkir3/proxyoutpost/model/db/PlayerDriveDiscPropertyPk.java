package me.darkkir3.proxyoutpost.model.db;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

@Embeddable
public class PlayerDriveDiscPropertyPk {

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

    /**
     * the id of the property this belongs to (in property.json)
     */
    @Column(name="propertyId", nullable = false)
    private long propertyId;

    /**
     * whether this is the main stat value of this property or not
     */
    @Column(name="isMainStat", nullable = false)
    private boolean isMainStat;

    public PlayerDriveDiscPropertyPk() {}

    public PlayerDriveDiscPropertyPk(Long profileUid, Long agentId, int slot, long propertyId, boolean isMainStat) {
        this.profileUid = profileUid;
        this.agentId = agentId;
        this.slot = slot;
        this.propertyId = propertyId;
        this.isMainStat = isMainStat;
    }

    @JsonIgnore
    public Long getProfileUid() {
        return profileUid;
    }

    @JsonIgnore
    public Long getAgentId() {
        return agentId;
    }

    @JsonIgnore
    public int getSlot() {
        return slot;
    }

    @JsonProperty("Id")
    public long getPropertyId() {
        return propertyId;
    }

    @JsonIgnore
    public boolean isMainStat() {
        return isMainStat;
    }
}
