package me.darkkir3.proxyoutpost.model.json;

import com.fasterxml.jackson.annotation.JsonProperty;

public class MainPropertyList {
   @JsonProperty("PropertyId")
   public int propertyId;
   @JsonProperty("PropertyLevel")
   public int propertyLevel;
   @JsonProperty("PropertyValue")
   public int propertyValue;
    }