package me.darkkir3.proxyoutpost.model.json;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class SocialDetail {
    @JsonProperty("MedalList")
    public List<MedalList> medalList;
    @JsonProperty("ProfileDetail")
    public ProfileDetail profileDetail;
    @JsonProperty("Desc")
    public String desc;
}