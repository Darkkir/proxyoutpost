package me.darkkir3.proxyoutpost.model.output;

import com.fasterxml.jackson.annotation.JsonProperty;

public class WeaponProperty {
    @JsonProperty("PropertyId")
    public Long propertyId;
    @JsonProperty("PropertyValue")
    public Long propertyValue;
}
