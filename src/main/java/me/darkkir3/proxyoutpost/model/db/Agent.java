package me.darkkir3.proxyoutpost.model.db;

import jakarta.persistence.*;
import me.darkkir3.proxyoutpost.controller.DBUtils;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name="agents")
public class Agent {

    /**
     * primary key of agent
     */
    @EmbeddedId
    AgentPk agentPk;

    /**
     * the profile this agent belongs to
     */
    @ManyToOne
    @JoinColumns({
            @JoinColumn(name="profileUid", referencedColumnName = "profileUid", insertable = false, updatable = false),
    })
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
     * id of the weapon equipped on this character
     */
    @Column(name="weaponUid")
    private Long weaponUid;

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
    @OneToMany(mappedBy = "agent")
    private List<SkillLevel> skillLevelList;

    /**
     * the currently equipped weapon on this agent
     */
    @OneToOne(mappedBy = "agent")
    private Weapon weapon;

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
}
