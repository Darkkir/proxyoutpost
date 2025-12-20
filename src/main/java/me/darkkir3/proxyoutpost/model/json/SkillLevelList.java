package me.darkkir3.proxyoutpost.model.json;

import com.fasterxml.jackson.annotation.JsonProperty;

public class SkillLevelList {
    @JsonProperty("Level")
    public int level;
    @JsonProperty("Index")
    public int index;
}
