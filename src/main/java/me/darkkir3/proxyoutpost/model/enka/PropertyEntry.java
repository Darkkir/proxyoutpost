package me.darkkir3.proxyoutpost.model.enka;

import com.fasterxml.jackson.annotation.JsonProperty;

public class PropertyEntry implements EnkaData {
   @JsonProperty("PropertyId")
   public long propertyId;
   @JsonProperty("PropertyLevel")
   public int propertyLevel;
   @JsonProperty("PropertyValue")
   public int propertyValue;
}