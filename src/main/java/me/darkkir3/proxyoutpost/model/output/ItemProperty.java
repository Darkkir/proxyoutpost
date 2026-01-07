package me.darkkir3.proxyoutpost.model.output;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * represents a weapon or drive disc (main or secondary) stat
 */
public class ItemProperty {

    /**
     * the id of this property in property.json
     */
    private long propertyId;

    /**
     * the value of this property
     */
    private int propertyValue;

    /**
     * the name of this property
     */
    private String propertyName;

    @JsonProperty("PropertyId")
    public long getPropertyId() {
        return propertyId;
    }

    @JsonProperty("PropertyId")
    public void setPropertyId(long propertyId) {
        this.propertyId = propertyId;
    }

    @JsonProperty("PropertyValue")
    public int getPropertyValue() {
        return propertyValue;
    }

    @JsonProperty("PropertyValue")
    public void setPropertyValue(int propertyValue) {
        this.propertyValue = propertyValue;
    }

    public String getPropertyName() {
        return propertyName;
    }

    public void setPropertyName(String propertyName) {
        this.propertyName = propertyName;
    }
}
