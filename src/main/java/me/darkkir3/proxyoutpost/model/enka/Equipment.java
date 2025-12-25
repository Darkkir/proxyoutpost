package me.darkkir3.proxyoutpost.model.enka;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class Equipment implements EnkaData {
    @JsonProperty("RandomPropertyList")
    public List<PropertyEntry> secondaryPropertyList;
    @JsonProperty("MainPropertyList")
    public List<PropertyEntry> mainPropertyList;
    @JsonProperty("IsAvailable")
    public boolean isAvailable;
    @JsonProperty("IsLocked")
    public boolean isLocked;
    @JsonProperty("IsTrash")
    public boolean isTrash;
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
    }