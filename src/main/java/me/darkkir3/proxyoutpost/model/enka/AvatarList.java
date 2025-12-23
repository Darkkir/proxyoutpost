package me.darkkir3.proxyoutpost.model.enka;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class AvatarList implements EnkaData {
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
   public long id;
   @JsonProperty("Level")
   public int level;
   @JsonProperty("PromotionLevel")
   public int promotionLevel;
   @JsonProperty("Exp")
   public int exp;
   @JsonProperty("SkinId")
   public long skinId;
   @JsonProperty("TalentLevel")
   public int talentLevel;
   @JsonProperty("UpgradeId")
   public long upgradeId;
   @JsonProperty("CoreSkillEnhancement")
   public int coreSkillEnhancement;
   @JsonProperty("WeaponUid")
   public long weaponUid;
   @JsonProperty("ObtainmentTimestamp")
   public long obtainmentTimestamp;
   @JsonProperty("PlayerWeapon")
   public Weapon weapon;
}