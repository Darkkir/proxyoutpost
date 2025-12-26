package me.darkkir3.proxyoutpost.equipment;

import com.fasterxml.jackson.annotation.JsonProperty;

public class DriveDiscRarity {
    @JsonProperty("Rarity")
    public int rarity;
    @JsonProperty("SuitId")
    public Long suitId;
}
