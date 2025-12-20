package me.darkkir3.proxyoutpost.model.enka;

import com.fasterxml.jackson.annotation.JsonProperty;

public class RandomPropertyList implements EnkaData {
   @JsonProperty("PropertyId")
   public int propertyId;
   @JsonProperty("PropertyLevel")
   public int propertyLevel;
   @JsonProperty("PropertyValue")
   public int propertyValue;
}