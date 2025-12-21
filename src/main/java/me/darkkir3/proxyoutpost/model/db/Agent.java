package me.darkkir3.proxyoutpost.model.db;

import jakarta.persistence.*;
import me.darkkir3.proxyoutpost.utils.DBUtils;
import me.darkkir3.proxyoutpost.model.enka.AvatarList;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="agents")
public class Agent implements EnkaToDBMapping<AvatarList> {

    /**
     * primary key of agent
     */
    @EmbeddedId
    AgentPk agentPk;

    /**
     * the profile this agent belongs to
     */
    @ManyToOne
    @JoinColumn(name="profileUid", referencedColumnName = "profileUid", insertable = false, updatable = false)
    private Profile profile;

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
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "agent")
    private List<SkillLevel> skillLevelList;

    /**
     * the currently equipped weapon on this agent
     */
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "agent")
    private Weapon weapon;

    public Agent() {}

    public Agent(AgentPk agentPk) {
        this.agentPk = agentPk;
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

    public List<SkillLevel> getSkillLevelList() {
        return skillLevelList;
    }

    public void setSkillLevelList(List<SkillLevel> skillLevelList) {
        this.skillLevelList = skillLevelList;
    }

    public Weapon getWeapon() {
        return weapon;
    }

    public void setWeapon(Weapon weapon) {
        this.weapon = weapon;
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

    public AgentPk getAgentPk() {
        return agentPk;
    }

    public Profile getProfile() {
        return profile;
    }

    @Override
    public void mapEnkaDataToDB(AvatarList enkaData) {
        if(this.agentPk != null && enkaData != null) {
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
                Weapon weaponFromEnka = new Weapon(
                        new WeaponPk(
                                this.agentPk.getProfileUid(),
                                this.agentPk.getAgentId(),
                                enkaData.weaponUid));
                weaponFromEnka.mapEnkaDataToDB(enkaData.weapon);
                this.setWeapon(weaponFromEnka);
            }

            if(enkaData.skillLevelList != null) {
                List<SkillLevel> skillLevels = new ArrayList<>();
                enkaData.skillLevelList.forEach(t -> {
                    SkillLevel level = new SkillLevel(
                            new SkillLevelPk(
                                    this.agentPk.getProfileUid(),
                                    this.agentPk.getAgentId(),
                                    t.index));
                    level.mapEnkaDataToDB(t);
                    skillLevels.add(level);
                });
                this.setSkillLevelList(skillLevels);
            }
        }
    }
}
