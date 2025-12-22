package me.darkkir3.proxyoutpost.model.enka;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ZZZProfile implements EnkaData {
   @JsonProperty("PlayerInfo")
   public PlayerInfo playerInfo;
   public long uid;
   public long ttl;
}