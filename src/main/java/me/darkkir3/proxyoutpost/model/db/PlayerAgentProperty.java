package me.darkkir3.proxyoutpost.model.db;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonUnwrapped;
import jakarta.persistence.*;
import me.darkkir3.proxyoutpost.model.output.PropertyOutput;

import java.util.Objects;

@Entity
@Table(name="agent_properties")
public class PlayerAgentProperty {

    /**
     * primary key of agent property
     */
    @EmbeddedId
    private PlayerAgentPropertyPk playerAgentPropertyPk;

    /**
     * the base value of this property
     * (based on agent level and core skill and promotion level)
     */
    @Column(name="baseValue")
    private int baseValue;

    /**
     * the total value of this property
     * (based on base value + drive discs + engine)
     */
    @Column(name="totalValue")
    private int totalValue;

    /**
     * the total number of substat rolls that went into this property
     * (based on equipped drive discs)
     */
    @Column(name="totalRolls")
    private int totalRolls;

    /**
     * the translated property name
     */
    @Transient
    private PropertyOutput propertyOutput;

    /**
     * the playerAgent this property belongs to
     */
    @JsonIgnore
    @ManyToOne
    @JoinColumn(name="profileUid", referencedColumnName = "profileUid", insertable = false, updatable = false)
    @JoinColumn(name="agentId", referencedColumnName = "agentId", insertable = false, updatable = false)
    private PlayerAgent playerAgent;

    public PlayerAgentProperty() {}

    public PlayerAgentProperty(PlayerAgentPropertyPk playerAgentPropertyPk) {
        this.playerAgentPropertyPk = playerAgentPropertyPk;
    }

    @JsonUnwrapped
    public PlayerAgentPropertyPk getPlayerAgentPropertyPk() {
        return playerAgentPropertyPk;
    }


    @JsonIgnore
    public PlayerAgent getAgent() {
        return playerAgent;
    }

    @JsonProperty("BaseValue")
    public int getBaseValue() {
        return baseValue;
    }

    public void setBaseValue(int baseValue) {
        this.baseValue = baseValue;
    }

    @JsonProperty("TotalValue")
    public int getTotalValue() {
        return totalValue;
    }

    public void setTotalValue(int totalValue) {
        this.totalValue = totalValue;
    }

    @JsonProperty("TotalRolls")
    public int getTotalRolls() {
        return totalRolls;
    }

    public void setTotalRolls(int totalRolls) {
        this.totalRolls = totalRolls;
    }

    /**
     * @return the translated property name
     */
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonUnwrapped
    public PropertyOutput getPropertyOutput() {
        return this.propertyOutput;
    }

    /**
     * set the translated property value
     */
    public void setPropertyOutput(PropertyOutput value) {
        this.propertyOutput = value;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof PlayerAgentProperty that)) return false;
        return Objects.equals(playerAgentPropertyPk, that.playerAgentPropertyPk);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(playerAgentPropertyPk);
    }
}
