package me.darkkir3.proxyoutpost.model.hoyowiki;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;
import java.util.Map;

public record HoyoWikiAgent(
        String fullName,
        String description,
        String iconUrl,
        String headerImgUrl,
        Map<String, String> specialtyMap,
        @JsonProperty("typeMap")
        Map<String, String> typeMap,
        @JsonProperty("factionMap")
        Map<String, String> factionMap,
        @JsonProperty("mindscapes")
        List<HoyoMindscape> hoyoMindscapes
) {}
