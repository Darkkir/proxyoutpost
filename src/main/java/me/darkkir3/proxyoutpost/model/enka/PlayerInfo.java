package me.darkkir3.proxyoutpost.model.enka;

import com.fasterxml.jackson.annotation.JsonProperty;

public class PlayerInfo implements EnkaData {
   @JsonProperty("ShowcaseDetail")
   public ShowcaseDetail showcaseDetail;
   @JsonProperty("SocialDetail")
   public SocialDetail socialDetail;
}