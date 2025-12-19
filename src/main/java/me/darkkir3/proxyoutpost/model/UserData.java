package me.darkkir3.proxyoutpost.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;

public class UserData {
    public class AvatarList{
        @JsonProperty("EquippedList")
        public ArrayList<EquippedList> equippedList;
        @JsonProperty("SkillLevelList")
        public ArrayList<SkillLevelList> skillLevelList;
        @JsonProperty("TalentToggleList")
        public ArrayList<Boolean> talentToggleList;
        @JsonProperty("ClaimedRewardList")
        public ArrayList<Integer> claimedRewardList;
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

    public class Equipment{
        @JsonProperty("RandomPropertyList")
        public ArrayList<RandomPropertyList> randomPropertyList;
        @JsonProperty("MainPropertyList")
        public ArrayList<MainPropertyList> mainPropertyList;
        @JsonProperty("IsAvailable")
        public boolean isAvailable;
        @JsonProperty("IsLocked")
        public boolean isLocked;
        @JsonProperty("IsTrash")
        public boolean isTrash;
        @JsonProperty("Id")
        public int id;
        @JsonProperty("Level")
        public int level;
        @JsonProperty("Uid")
        public int uid;
        @JsonProperty("BreakLevel")
        public int breakLevel;
        @JsonProperty("Exp")
        public int exp;
    }

    public class EquippedList{
        @JsonProperty("Slot")
        public int slot;
        @JsonProperty("Equipment")
        public Equipment equipment;
    }

    public class MainPropertyList{
        @JsonProperty("PropertyId")
        public int propertyId;
        @JsonProperty("PropertyLevel")
        public int propertyLevel;
        @JsonProperty("PropertyValue")
        public int propertyValue;
    }

    public class MedalList{
        @JsonProperty("JNJGABNIHMA")
        public boolean jNJGABNIHMA;
        @JsonProperty("Value")
        public int value;
        @JsonProperty("MedalScore")
        public int medalScore;
        @JsonProperty("MedalIcon")
        public int medalIcon;
        @JsonProperty("MedalType")
        public int medalType;
    }

    public class PlayerInfo{
        @JsonProperty("ShowcaseDetail")
        public ShowcaseDetail showcaseDetail;
        @JsonProperty("SocialDetail")
        public SocialDetail socialDetail;
    }

    public class ProfileDetail{
        @JsonProperty("TitleInfo")
        public TitleInfo titleInfo;
        @JsonProperty("Nickname")
        public String nickname;
        @JsonProperty("AvatarId")
        public int avatarId;
        @JsonProperty("Level")
        public int level;
        @JsonProperty("Uid")
        public int uid;
        @JsonProperty("Title")
        public int title;
        @JsonProperty("ProfileId")
        public int profileId;
        @JsonProperty("PlatformType")
        public int platformType;
        @JsonProperty("CallingCardId")
        public int callingCardId;
    }

    public class RandomPropertyList{
        @JsonProperty("PropertyId")
        public int propertyId;
        @JsonProperty("PropertyLevel")
        public int propertyLevel;
        @JsonProperty("PropertyValue")
        public int propertyValue;
    }

    public class Root{
        @JsonProperty("PlayerInfo")
        public PlayerInfo playerInfo;
        public String uid;
        public int ttl;
    }

    public class ShowcaseDetail{
        @JsonProperty("AvatarList")
        public ArrayList<AvatarList> avatarList;
    }

    public class SkillLevelList{
        @JsonProperty("Level")
        public int level;
        @JsonProperty("Index")
        public int index;
    }

    public class SocialDetail{
        @JsonProperty("MedalList")
        public ArrayList<MedalList> medalList;
        @JsonProperty("ProfileDetail")
        public ProfileDetail profileDetail;
        @JsonProperty("Desc")
        public String desc;
    }

    public class TitleInfo{
        @JsonProperty("Args")
        public ArrayList<String> args;
        @JsonProperty("Title")
        public int title;
        @JsonProperty("FullTitle")
        public int fullTitle;
    }

    public class Weapon{
        @JsonProperty("IsAvailable")
        public boolean isAvailable;
        @JsonProperty("IsLocked")
        public boolean isLocked;
        @JsonProperty("Id")
        public int id;
        @JsonProperty("Level")
        public int level;
        @JsonProperty("Uid")
        public int uid;
        @JsonProperty("BreakLevel")
        public int breakLevel;
        @JsonProperty("Exp")
        public int exp;
        @JsonProperty("UpgradeLevel")
        public int upgradeLevel;
    }


}
