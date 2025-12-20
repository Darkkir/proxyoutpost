package me.darkkir3.proxyoutpost.model.json;

import com.fasterxml.jackson.annotation.JsonProperty;

public class EquippedList {
   @JsonProperty("Slot")
   public int slot;
   @JsonProperty("Equipment")
   public Equipment equipment;
    }