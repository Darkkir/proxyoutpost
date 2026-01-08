package me.darkkir3.proxyoutpost.model.db;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonUnwrapped;
import jakarta.persistence.*;
import me.darkkir3.proxyoutpost.model.enka.AvatarList;
import me.darkkir3.proxyoutpost.model.output.AgentOutput;
import me.darkkir3.proxyoutpost.utils.DBUtils;

import java.time.LocalDateTime;
import java.util.*;

@Entity
@Table(name="agents")
public class PlayerAgent implements EnkaToDBMapping<AvatarList> {

    /**
     * primary key of agent
     */
    @EmbeddedId
    private PlayerAgentPk playerAgentPk;

    /**
     * the playerProfile this agent belongs to
     */
    @ManyToOne
    @JoinColumn(name="profileUid", referencedColumnName = "profileUid", insertable = false, updatable = false)
    private PlayerProfile playerProfile;

    /**
     * agent level
     */
    @Column(name="AgentLevel")
    private int agentLevel;

    /**
     * agent promotion level
     */
    @Column(name="agentPromotionLevel")
    private int agentPromotionLevel;

    /**
     * skin id for that agent
     */
    @Column(name="skinId")
    private Long skinId;

    /**
     * mindscape level of this agent (talentLevel in JSON)
     */
    @Column(name="mindscapeLevel")
    private int mindscapeLevel;

    /**
     * mindscape cinema visual toggles (talentToggleList in JSON)
     */
    @Column(name="mindscapeToggleList")
    private String mindscapeToggleList;

    /**
     * not sure what this field does (potential unlock?)
     */
    @Column(name="upgradeId")
    private Long upgradeId;

    /**
     * core skill enhancement (A,B,C,D,E,F from 0 to 6)
     */
    @Column(name="coreSkillEnhancement")
    private int coreSkillEnhancement;

    /**
     * w-engine signature special effect state [0: None, 1: OFF, 2: ON]
     */
    @Column(name="weaponEffectState")
    private int weaponEffectState;

    /**
     * the timestamp of when we obtained this agent
     */
    @Column(name="obtainmentTimestamp")
    private LocalDateTime obtainmentTimestamp;
    /**
     * whether the agent was marked as favorite in-game
     */
    @Column(name="isFavorite")
    private boolean isFavorite;

    /**
     * not sure what this does (potential unlock?)
     */
    @Column(name="isUpgradeUnlocked")
    private boolean isUpgradeUnlocked;

    /**
     * which ascension rewards are claimed for this agent
     */
    @Column(name="claimedRewardList")
    private String claimedRewardList;

    /**
     * list of skills with their associated level for this agent
     */
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "playerAgent")
    @Column(name="skillLevelList")
    private List<PlayerSkillLevel> playerSkillLevelList;

    /**
     * list of the drive discs this agent has equipped
     */
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "playerAgent")
    @Column(name="driveDiscList")
    private List<PlayerDriveDisc> playerDriveDiscs;

    /**
     * the base stats of each agent
     */
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "playerAgent")
    @Column(name="propertyList")
    private Map<Long, PlayerAgentProperty> propertyMap;

    /**
     * the currently equipped playerWeapon on this agent
     */
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "playerAgent")
    private PlayerWeapon playerWeapon;

    /**
     * the generic agent data to include with this agent
     */
    @Transient
    private AgentOutput agentOutput;

    public PlayerAgent() {}

    public PlayerAgent(PlayerAgentPk playerAgentPk) {
        this.playerAgentPk = playerAgentPk;
    }

    @JsonProperty("Level")
    public int getAgentLevel() {
        return agentLevel;
    }

    public void setAgentLevel(int agentLevel) {
        this.agentLevel = agentLevel;
    }

    @JsonProperty("PromotionLevel")
    public int getAgentPromotionLevel() {
        return agentPromotionLevel;
    }

    public void setAgentPromotionLevel(int agentPromotionLevel) {
        this.agentPromotionLevel = agentPromotionLevel;
    }

    @JsonIgnore
    @JsonProperty("SkinId")
    public Long getSkinId() {
        return skinId;
    }

    public void setSkinId(Long skinId) {
        this.skinId = skinId;
    }

    @JsonProperty("MindscapeLevel")
    public int getMindscapeLevel() {
        return mindscapeLevel;
    }

    public void setMindscapeLevel(int mindscapeLevel) {
        this.mindscapeLevel = mindscapeLevel;
    }

    @JsonIgnore
    @JsonProperty("UpgradeId")
    public Long getUpgradeId() {
        return upgradeId;
    }

    public void setUpgradeId(Long upgradeId) {
        this.upgradeId = upgradeId;
    }

    @JsonProperty("CoreSkillEnhancement")
    public int getCoreSkillEnhancement() {
        return coreSkillEnhancement;
    }

    public void setCoreSkillEnhancement(int coreSkillEnhancement) {
        this.coreSkillEnhancement = coreSkillEnhancement;
    }

    @JsonProperty("WeaponEffectState")
    public int getWeaponEffectState() {
        return weaponEffectState;
    }

    public void setWeaponEffectState(int weaponEffectState) {
        this.weaponEffectState = weaponEffectState;
    }

    @JsonProperty("ObtainmentTimestamp")
    public LocalDateTime getObtainmentTimestamp() {
        return obtainmentTimestamp;
    }

    public void setObtainmentTimestamp(LocalDateTime obtainmentTimestamp) {
        this.obtainmentTimestamp = obtainmentTimestamp;
    }

    @JsonProperty("IsFavorite")
    public boolean isFavorite() {
        return isFavorite;
    }

    public void setFavorite(boolean favorite) {
        isFavorite = favorite;
    }

    @JsonIgnore
    @JsonProperty("IsUpgradeUnlocked")
    public boolean isUpgradeUnlocked() {
        return isUpgradeUnlocked;
    }

    public void setUpgradeUnlocked(boolean upgradeUnlocked) {
        isUpgradeUnlocked = upgradeUnlocked;
    }

    @JsonProperty("SkillLevelList")
    public List<PlayerSkillLevel> getSkillLevelList() {
        return playerSkillLevelList;
    }

    public void setSkillLevelList(List<PlayerSkillLevel> playerSkillLevelList) {
        this.playerSkillLevelList = playerSkillLevelList;
    }

    @JsonProperty("DriveDiscList")
    public List<PlayerDriveDisc> getPlayerDriveDiscs() {
        return playerDriveDiscs;
    }

    public void setPlayerDriveDiscs(List<PlayerDriveDisc> playerDriveDiscs) {
        this.playerDriveDiscs = playerDriveDiscs;
    }

    @JsonProperty("Weapon")
    public PlayerWeapon getWeapon() {
        return playerWeapon;
    }

    public void setWeapon(PlayerWeapon playerWeapon) {
        this.playerWeapon = playerWeapon;
    }

    @JsonProperty("MindscapeToggleList")
    public List<Boolean> getMindscapeToggleList() {
        return DBUtils.convertStringToBooleanList(this.mindscapeToggleList);
    }

    public void setMindscapeToggleList(List<Boolean> values) {
        this.mindscapeToggleList = DBUtils.convertListToField(values);
    }

    @JsonIgnore
    @JsonProperty("ClaimedRewardList")
    public List<Integer> getClaimedRewardList() {
        return DBUtils.convertStringToIntegerList(this.claimedRewardList);
    }

    public void setClaimedRewardList(List<Integer> values) {
        this.claimedRewardList = DBUtils.convertListToField(values);
    }

    @JsonIgnore
    public Map<Long, PlayerAgentProperty> getPropertyMap() {
        return propertyMap;
    }

    /**
     * @return only the values of the agent properties for JSON parsing
     */
    @JsonProperty("PropertyList")
    public Collection<PlayerAgentProperty> getPropertyCollection() {
        if (this.propertyMap != null) {
            return this.propertyMap.values();
        }

        return Collections.emptyList();
    }

    public void setPropertyMap(Map<Long, PlayerAgentProperty> propertyMap) {
        this.propertyMap = propertyMap;
    }

    @JsonIgnore
    public PlayerAgentPk getAgentPk() {
        return playerAgentPk;
    }

    @JsonIgnore
    public PlayerProfile getProfile() {
        return playerProfile;
    }

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonUnwrapped
    public AgentOutput getAgentOutput() {
        return agentOutput;
    }

    public void setAgentOutput(AgentOutput agentOutput) {
        this.agentOutput = agentOutput;
    }

    @Override
    public void mapEnkaDataToDB(AvatarList enkaData) {
        if(this.playerAgentPk != null && enkaData != null) {
            this.setAgentLevel(enkaData.level);
            this.setAgentPromotionLevel(enkaData.promotionLevel);
            this.setSkinId(enkaData.skinId);
            this.setMindscapeLevel(enkaData.talentLevel);
            this.setMindscapeToggleList(enkaData.talentToggleList);
            this.setUpgradeId(enkaData.upgradeId);
            this.setCoreSkillEnhancement(enkaData.coreSkillEnhancement);
            this.setWeaponEffectState(enkaData.weaponEffectState);
            this.setObtainmentTimestamp(DBUtils.localDateTimeFromJson(enkaData.obtainmentTimestamp));
            this.setFavorite(enkaData.isFavorite);
            this.setUpgradeUnlocked(enkaData.isUpgradeUnlocked);
            this.setClaimedRewardList(enkaData.claimedRewardList);

            if(enkaData.weapon != null) {
                PlayerWeapon playerWeaponFromEnka = new PlayerWeapon(
                        new PlayerWeaponPk(
                                this.playerAgentPk.getProfileUid(),
                                this.playerAgentPk.getAgentId(),
                                enkaData.weaponUid));
                playerWeaponFromEnka.mapEnkaDataToDB(enkaData.weapon);
                this.setWeapon(playerWeaponFromEnka);
            }

            if(enkaData.skillLevelList != null) {
                List<PlayerSkillLevel> playerSkillLevels = new ArrayList<>();
                enkaData.skillLevelList.forEach(t -> {
                    PlayerSkillLevel level = new PlayerSkillLevel(
                            new PlayerSkillLevelPk(
                                    this.playerAgentPk.getProfileUid(),
                                    this.playerAgentPk.getAgentId(),
                                    t.index));
                    level.mapEnkaDataToDB(t);

                    playerSkillLevels.add(level);
                });
                this.setSkillLevelList(playerSkillLevels);
            }

            if(enkaData.equippedList != null) {
                List<PlayerDriveDisc> driveDiscsToEquip = new ArrayList<>();
                enkaData.equippedList.forEach(t -> {
                    PlayerDriveDisc driveDisc = new PlayerDriveDisc(
                            new PlayerDriveDiscPk(
                                    this.playerAgentPk.getProfileUid(),
                                    this.playerAgentPk.getAgentId(),
                                    t.slot));
                    driveDisc.mapEnkaDataToDB(t);

                    driveDiscsToEquip.add(driveDisc);
                });
                this.setPlayerDriveDiscs(driveDiscsToEquip);
            }
        }
    }
}
