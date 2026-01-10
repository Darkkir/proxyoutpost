package me.darkkir3.proxyoutpost.model.db;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonUnwrapped;
import jakarta.persistence.*;
import me.darkkir3.proxyoutpost.model.enka.PropertyEntry;
import me.darkkir3.proxyoutpost.model.output.PropertyOutput;

@Entity
@Table(name="drive_disc_properties")
public class PlayerDriveDiscProperty implements EnkaToDBMapping<PropertyEntry> {

    /**
     * the disc property this belongs to
     */
    @EmbeddedId
    private PlayerDriveDiscPropertyPk playerDriveDiscPropertyPk;

    /**
     * how often this specific stat has been upgraded
     * (used by secondary stats)
     */
    @Column(name="propertyLevel")
    private int propertyLevel;

    /**
     * the actual value of this property
     */
    @Column(name="propertyValue")
    private int propertyValue;

    /**
     * the translated property name
     */
    @Transient
    private PropertyOutput propertyOutput;

    /**
     * the playerAgent this drive disc belongs to
     */
    @JsonIgnore
    @ManyToOne
    @JoinColumn(name="profileUid", referencedColumnName = "profileUid", insertable = false, updatable = false)
    @JoinColumn(name="agentId", referencedColumnName = "agentId", insertable = false, updatable = false)
    @JoinColumn(name="slot", referencedColumnName = "slot", insertable = false, updatable = false)
    private PlayerDriveDisc playerDriveDisc;

    public PlayerDriveDiscProperty() {}

    @JsonIgnore
    public PlayerDriveDiscProperty(PlayerDriveDiscPropertyPk playerDriveDiscPropertyPk) {
        this.playerDriveDiscPropertyPk = playerDriveDiscPropertyPk;
    }

    @JsonUnwrapped
    public PlayerDriveDiscPropertyPk getPlayerDriveDiscPropertyPk() {
        return playerDriveDiscPropertyPk;
    }

    @JsonProperty("level")
    public int getPropertyLevel() {
        return propertyLevel;
    }

    public void setPropertyLevel(int propertyLevel) {
        this.propertyLevel = propertyLevel;
    }

    @JsonProperty("value")
    public int getPropertyValue() {
        return propertyValue;
    }

    public void setPropertyValue(int propertyValue) {
        this.propertyValue = propertyValue;
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
    public void mapEnkaDataToDB(PropertyEntry enkaData) {
        if(enkaData != null) {
            this.propertyLevel = enkaData.propertyLevel;
            this.propertyValue = enkaData.propertyValue;
        }
    }
}
