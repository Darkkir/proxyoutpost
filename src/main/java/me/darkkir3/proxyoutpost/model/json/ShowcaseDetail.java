package me.darkkir3.proxyoutpost.model.json;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class ShowcaseDetail{
    @JsonProperty("AvatarList")
    public List<AvatarList> avatarList;
}
