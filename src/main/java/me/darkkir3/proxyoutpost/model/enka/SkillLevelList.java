package me.darkkir3.proxyoutpost.model.enka;

import com.fasterxml.jackson.annotation.JsonProperty;

public class SkillLevelList implements EnkaData {
    @JsonProperty("Level")
    public int level;
    @JsonProperty("Index")
    public int index;
}
