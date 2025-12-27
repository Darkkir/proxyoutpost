package me.darkkir3.proxyoutpost.model.db;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.util.Objects;

@Embeddable
public class PlayerAgentPropertyPk {

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
     * the actual property id from property.json
     */
    @JsonIgnore
    @Column(name="propertyId", nullable = false)
    private Long propertyId;

    public PlayerAgentPropertyPk() {}

    public PlayerAgentPropertyPk(Long profileUid, Long agentId, Long propertyId) {
        this.profileUid = profileUid;
        this.agentId = agentId;
        this.propertyId = propertyId;
    }

    @JsonIgnore
    public Long getProfileUid() {
        return profileUid;
    }

    @JsonIgnore
    public Long getAgentId() {
        return agentId;
    }

    @JsonProperty("PropertyId")
    public Long getPropertyId() {
        return propertyId;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof PlayerAgentPropertyPk that)) return false;
        return Objects.equals(propertyId, that.propertyId)
                && Objects.equals(profileUid, that.profileUid)
                && Objects.equals(agentId, that.agentId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(profileUid, agentId, propertyId);
    }
}
