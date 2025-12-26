package me.darkkir3.proxyoutpost.equipment;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Map;

public class DriveDiscLibrary {
    @JsonProperty("Items")
    public Map<Long, DriveDiscRarity> driveDiscRarityMap;
    @JsonProperty("Suits")
    public Map<Long, DriveDiscSuit> driveDiscSuitMap;
}
