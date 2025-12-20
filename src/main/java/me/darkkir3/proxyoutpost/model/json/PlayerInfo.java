package me.darkkir3.proxyoutpost.model.json;

import com.fasterxml.jackson.annotation.JsonProperty;

public class PlayerInfo {
   @JsonProperty("ShowcaseDetail")
   public ShowcaseDetail showcaseDetail;
   @JsonProperty("SocialDetail")
   public SocialDetail socialDetail;
}