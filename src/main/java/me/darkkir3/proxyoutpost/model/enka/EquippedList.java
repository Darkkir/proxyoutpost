package me.darkkir3.proxyoutpost.model.enka;

import com.fasterxml.jackson.annotation.JsonProperty;

public class EquippedList implements EnkaData {
   @JsonProperty("Slot")
   public int slot;
   @JsonProperty("Equipment")
   public Equipment equipment;
    }