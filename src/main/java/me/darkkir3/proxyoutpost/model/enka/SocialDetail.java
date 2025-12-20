package me.darkkir3.proxyoutpost.model.enka;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class SocialDetail implements EnkaData {
    @JsonProperty("MedalList")
    public List<MedalList> medalList;
    @JsonProperty("ProfileDetail")
    public ProfileDetail profileDetail;
    @JsonProperty("Desc")
    public String desc;
}