package me.darkkir3.proxyoutpost.model.json;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ZZZProfile {
   @JsonProperty("PlayerInfo")
   public PlayerInfo playerInfo;
   public String uid;
   public int ttl;
}