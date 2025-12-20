package me.darkkir3.proxyoutpost.model.enka;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ProfileDetail implements EnkaData {
   @JsonProperty("TitleInfo")
   public TitleInfo titleInfo;
   @JsonProperty("Nickname")
   public String nickname;
   @JsonProperty("AvatarId")
   public long avatarId;
   @JsonProperty("Level")
   public int level;
   @JsonProperty("Uid")
   public int uid;
   @JsonProperty("Title")
   public long title;
   @JsonProperty("ProfileId")
   public long profileId;
   @JsonProperty("PlatformType")
   public int platformType;
   @JsonProperty("CallingCardId")
   public long callingCardId;
}