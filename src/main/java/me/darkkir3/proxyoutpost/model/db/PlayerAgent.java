package me.darkkir3.proxyoutpost.model.db;

import jakarta.persistence.*;
import me.darkkir3.proxyoutpost.utils.DBUtils;
import me.darkkir3.proxyoutpost.model.enka.AvatarList;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="agents")
public class PlayerAgent implements EnkaToDBMapping<AvatarList> {

    /**
     * primary key of agent
     */
    @EmbeddedId
    PlayerAgentPk playerAgentPk;

    /**
     * the playerProfile this agent belongs to
     */
    @ManyToOne
    @JoinColumn(name="profileUid", referencedColumnName = "profileUid", insertable = false, updatable = false)
    private PlayerProfile playerProfile;

    /**
     * agent level
     */
    @Column(name="agentLevel")
    private int agentLevel;

    /**
     * agent promotion level
     */
    @Column(name="agentPromotionLevel")
    private int agentPromotionLevel;

    /**
     * agent exp
     */
    @Column(name="exp")
    private int exp;

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
     * the currently equipped playerWeapon on this agent
     */
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "playerAgent")
    private PlayerWeapon playerWeapon;

    public PlayerAgent() {}

    public PlayerAgent(PlayerAgentPk playerAgentPk) {
        this.playerAgentPk = playerAgentPk;
    }

    public int getAgentLevel() {
        return agentLevel;
    }

    public void setAgentLevel(int agentLevel) {
        this.agentLevel = agentLevel;
    }

    public int getAgentPromotionLevel() {
        return agentPromotionLevel;
    }

    public void setAgentPromotionLevel(int agentPromotionLevel) {
        this.agentPromotionLevel = agentPromotionLevel;
    }

    public int getExp() {
        return exp;
    }

    public void setExp(int exp) {
        this.exp = exp;
    }

    public Long getSkinId() {
        return skinId;
    }

    public void setSkinId(Long skinId) {
        this.skinId = skinId;
    }

    public int getMindscapeLevel() {
        return mindscapeLevel;
    }

    public void setMindscapeLevel(int mindscapeLevel) {
        this.mindscapeLevel = mindscapeLevel;
    }

    public Long getUpgradeId() {
        return upgradeId;
    }

    public void setUpgradeId(Long upgradeId) {
        this.upgradeId = upgradeId;
    }

    public int getCoreSkillEnhancement() {
        return coreSkillEnhancement;
    }

    public void setCoreSkillEnhancement(int coreSkillEnhancement) {
        this.coreSkillEnhancement = coreSkillEnhancement;
    }

    public int getWeaponEffectState() {
        return weaponEffectState;
    }

    public void setWeaponEffectState(int weaponEffectState) {
        this.weaponEffectState = weaponEffectState;
    }

    public LocalDateTime getObtainmentTimestamp() {
        return obtainmentTimestamp;
    }

    public void setObtainmentTimestamp(LocalDateTime obtainmentTimestamp) {
        this.obtainmentTimestamp = obtainmentTimestamp;
    }

    public boolean isFavorite() {
        return isFavorite;
    }

    public void setFavorite(boolean favorite) {
        isFavorite = favorite;
    }

    public boolean isUpgradeUnlocked() {
        return isUpgradeUnlocked;
    }

    public void setUpgradeUnlocked(boolean upgradeUnlocked) {
        isUpgradeUnlocked = upgradeUnlocked;
    }

    public List<PlayerSkillLevel> getSkillLevelList() {
        return playerSkillLevelList;
    }

    public void setSkillLevelList(List<PlayerSkillLevel> playerSkillLevelList) {
        this.playerSkillLevelList = playerSkillLevelList;
    }

    public PlayerWeapon getWeapon() {
        return playerWeapon;
    }

    public void setWeapon(PlayerWeapon playerWeapon) {
        this.playerWeapon = playerWeapon;
    }

    public List<Boolean> getMindscapeToggleList() {
        return DBUtils.convertStringToBooleanList(this.mindscapeToggleList);
    }

    public void setMindscapeToggleList(List<Boolean> values) {
        this.mindscapeToggleList = DBUtils.convertListToField(values);
    }

    public List<Integer> getClaimedRewardList() {
        return DBUtils.convertStringToIntegerList(this.claimedRewardList);
    }

    public void setClaimedRewardList(List<Integer> values) {
        this.claimedRewardList = DBUtils.convertListToField(values);
    }

    public PlayerAgentPk getAgentPk() {
        return playerAgentPk;
    }

    public PlayerProfile getProfile() {
        return playerProfile;
    }

    @Override
    public void mapEnkaDataToDB(AvatarList enkaData) {
        if(this.playerAgentPk != null && enkaData != null) {
            this.setAgentLevel(enkaData.level);
            this.setAgentPromotionLevel(enkaData.promotionLevel);
            this.setExp(enkaData.exp);
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
        }
    }
}
