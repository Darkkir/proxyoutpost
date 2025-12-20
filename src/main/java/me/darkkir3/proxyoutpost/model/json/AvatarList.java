package me.darkkir3.proxyoutpost.model.json;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class AvatarList {
   @JsonProperty("EquippedList")
   public List<EquippedList> equippedList;
   @JsonProperty("SkillLevelList")
   public List<SkillLevelList> skillLevelList;
   @JsonProperty("TalentToggleList")
   public List<Boolean> talentToggleList;
   @JsonProperty("ClaimedRewardList")
   public List<Integer> claimedRewardList;
   @JsonProperty("WeaponEffectState")
   public int weaponEffectState;
   @JsonProperty("IsFavorite")
   public boolean isFavorite;
   @JsonProperty("IsUpgradeUnlocked")
   public boolean isUpgradeUnlocked;
   @JsonProperty("Id")
   public int id;
   @JsonProperty("Level")
   public int level;
   @JsonProperty("PromotionLevel")
   public int promotionLevel;
   @JsonProperty("Exp")
   public int exp;
   @JsonProperty("SkinId")
   public int skinId;
   @JsonProperty("TalentLevel")
   public int talentLevel;
   @JsonProperty("UpgradeId")
   public int upgradeId;
   @JsonProperty("CoreSkillEnhancement")
   public int coreSkillEnhancement;
   @JsonProperty("WeaponUid")
   public int weaponUid;
   @JsonProperty("ObtainmentTimestamp")
   public int obtainmentTimestamp;
   @JsonProperty("Weapon")
   public Weapon weapon;
}