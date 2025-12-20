package me.darkkir3.proxyoutpost.model.json;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class Equipment {
    @JsonProperty("RandomPropertyList")
    public List<RandomPropertyList> randomPropertyList;
    @JsonProperty("MainPropertyList")
    public List<MainPropertyList> mainPropertyList;
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