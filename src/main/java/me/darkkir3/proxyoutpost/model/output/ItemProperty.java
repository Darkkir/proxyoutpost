package me.darkkir3.proxyoutpost.model.output;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * represents a weapon or drive disc (main or secondary) stat
 */
public class ItemProperty {

    /**
     * the id of this property in property.json
     */
    @JsonProperty("PropertyId")
    public long propertyId;

    /**
     * the value of this property
     */
    @JsonProperty("PropertyValue")
    public int propertyValue;
}
