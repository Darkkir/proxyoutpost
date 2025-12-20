package me.darkkir3.proxyoutpost.model.json;

import com.fasterxml.jackson.annotation.JsonProperty;

public class RandomPropertyList {
   @JsonProperty("PropertyId")
   public int propertyId;
   @JsonProperty("PropertyLevel")
   public int propertyLevel;
   @JsonProperty("PropertyValue")
   public int propertyValue;
}