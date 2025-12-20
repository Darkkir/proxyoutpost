package me.darkkir3.proxyoutpost.model.enka;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class TitleInfo implements EnkaData {
    @JsonProperty("Args")
    public List<String> args;
    @JsonProperty("Title")
    public int title;
    @JsonProperty("FullTitle")
    public int fullTitle;
}
