package me.darkkir3.proxyoutpost.model.output;

import com.fasterxml.jackson.annotation.JsonProperty;

public class WeaponProperty {
    @JsonProperty("PropertyId")
    public int propertyId;
    @JsonProperty("PropertyValue")
    public int propertyValue;
}
