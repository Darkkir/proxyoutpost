package me.darkkir3.proxyoutpost.model.enka;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class ShowcaseDetail implements EnkaData {
    @JsonProperty("AvatarList")
    public List<AvatarList> avatarList;
}
