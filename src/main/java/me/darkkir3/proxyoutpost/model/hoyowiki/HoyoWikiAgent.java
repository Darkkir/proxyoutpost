package me.darkkir3.proxyoutpost.model.hoyowiki;

import com.fasterxml.jackson.annotation.JsonProperty;
import tools.jackson.databind.annotation.JsonSerialize;
import me.darkkir3.proxyoutpost.utils.MapToArraySerializer;

import java.util.List;
import java.util.Map;

public record HoyoWikiAgent(
        String fullName,
        String description,
        String iconUrl,
        String headerImgUrl,
        @JsonSerialize(using = MapToArraySerializer.class)
        Map<String, String> specialtyMap,
        @JsonSerialize(using = MapToArraySerializer.class)
        @JsonProperty("typeMap")
        Map<String, String> typeMap,
        @JsonSerialize(using = MapToArraySerializer.class)
        @JsonProperty("factionMap")
        Map<String, String> factionMap,
        @JsonProperty("mindscapes")
        List<HoyoMindscape> hoyoMindscapes
) {}
