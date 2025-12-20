package me.darkkir3.proxyoutpost.model.enka;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Weapon implements EnkaData {
    @JsonProperty("IsAvailable")
    public boolean isAvailable;
    @JsonProperty("IsLocked")
    public boolean isLocked;
    @JsonProperty("Id")
    public int id;
    @JsonProperty("Level")
    public int level;
    @JsonProperty("Uid")
    public int uid;
    @JsonProperty("BreakLevel")
    public int breakLevel;
    @JsonProperty("Exp")
    public int exp;
    @JsonProperty("UpgradeLevel")
    public int upgradeLevel;
}
