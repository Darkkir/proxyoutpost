package me.darkkir3.proxyoutpost.model.enka;

import com.fasterxml.jackson.annotation.JsonProperty;

public class MedalList implements EnkaData {
   @JsonProperty("JNJGABNIHMA")
   public boolean jNJGABNIHMA;
   @JsonProperty("Value")
   public int value;
   @JsonProperty("MedalScore")
   public int medalScore;
   @JsonProperty("MedalIcon")
   public int medalIcon;
   @JsonProperty("MedalType")
   public int medalType;
}