package me.darkkir3.proxyoutpost.model.db;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonUnwrapped;
import jakarta.persistence.*;
import me.darkkir3.proxyoutpost.model.output.WeaponOutput;

@Entity
@Table(name="weapons")
public class PlayerWeapon implements EnkaToDBMapping<me.darkkir3.proxyoutpost.model.enka.Weapon> {

    /**
     * primary key of weapon
     */
    @EmbeddedId
    private PlayerWeaponPk playerWeaponPk;

    /**
     * not sure what this field does
     */
    @Column(name="isAvailable")
    private boolean isAvailable;

    /**
     * whether this weapon is locked for refinement/phasing
     */
    @Column(name="isLocked")
    private boolean isLocked;

    /**
     * the id of this weapon in weapons.json
     */
    @Column(name="weaponId")
    private Long weaponId;
    /**
     * the level of this weapon
     */
    @Column(name="level")
    private int level;

    /**
     * ascension/modification level (0-5)
     */
    @Column(name="breakLevel")
    private int breakLevel;

    /**
     * refinement/phase level (1-5)
     */
    @Column(name="upgradeLevel")
    private int upgradeLevel;

    /**
     * exp of this w-engine
     */
    @Column(name="exp")
    private int exp;

    /**
     * the main stat value of this w-engine
     */
    @Column(name="mainStat")
    private int mainStat;

    /**
     * the secondary stat value of this w-engine
     */
    @Column(name="secondaryStat")
    private int secondaryStat;

    /**
     * the generic weapon data to include with this agent
     */
    @Transient
    private WeaponOutput weaponOutput;

    @OneToOne
    @JoinColumn(name="profileUid", referencedColumnName = "profileUid", insertable = false, updatable = false)
    @JoinColumn(name="agentId", referencedColumnName = "agentId", insertable = false, updatable = false)
    private PlayerAgent playerAgent;

    public PlayerWeapon() {}

    public PlayerWeapon(PlayerWeaponPk playerWeaponPk) {
        this.playerWeaponPk = playerWeaponPk;
    }

    @JsonProperty("IsAvailable")
    public boolean isAvailable() {
        return isAvailable;
    }

    public void setAvailable(boolean available) {
        isAvailable = available;
    }

    @JsonProperty("IsLocked")
    public boolean isLocked() {
        return isLocked;
    }

    public void setLocked(boolean locked) {
        isLocked = locked;
    }

    @JsonProperty("WeaponId")
    public Long getWeaponId() {
        return weaponId;
    }

    public void setWeaponId(Long weaponId) {
        this.weaponId = weaponId;
    }

    @JsonProperty("Level")
    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    @JsonProperty("BreakLevel")
    public int getBreakLevel() {
        return breakLevel;
    }

    public void setBreakLevel(int breakLevel) {
        this.breakLevel = breakLevel;
    }

    @JsonProperty("UpgradeLevel")
    public int getUpgradeLevel() {
        return upgradeLevel;
    }

    public void setUpgradeLevel(int upgradeLevel) {
        this.upgradeLevel = upgradeLevel;
    }

    @JsonIgnore
    @JsonProperty("Exp")
    public int getExp() {
        return exp;
    }

    public void setExp(int exp) {
        this.exp = exp;
    }

    @JsonIgnore
    public PlayerAgent getAgent() {
        return playerAgent;
    }

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonUnwrapped
    public WeaponOutput getWeaponOutput() {
        return weaponOutput;
    }

    public void setWeaponOutput(WeaponOutput weaponOutput) {
        this.weaponOutput = weaponOutput;
    }

    @JsonProperty("MainStatValue")
    public int getMainStat() {
        return mainStat;
    }

    /**
     * @return true if the value of main stat is greater than zero
     */
    @JsonIgnore
    public boolean isMainStatSet() {
        return this.mainStat > 0d;
    }

    public void setMainStat(int mainStat) {
        this.mainStat = mainStat;
    }


    @JsonProperty("SecondaryStatValue")
    public int getSecondaryStat() {
        return secondaryStat;
    }

    /**
     * @return true if the value of secondary stat is greater than zero
     */
    @JsonIgnore
    public boolean isSecondaryStatSet() {
        return this.secondaryStat > 0d;
    }

    public void setSecondaryStat(int secondaryStat) {
        this.secondaryStat = secondaryStat;
    }

    @Override
    public void mapEnkaDataToDB(me.darkkir3.proxyoutpost.model.enka.Weapon enkaData) {
        if(this.playerWeaponPk != null && enkaData != null) {
            this.setAvailable(enkaData.isAvailable);
            this.setLocked(enkaData.isLocked);
            this.setWeaponId(enkaData.id);
            this.setLevel(enkaData.level);
            this.setBreakLevel(enkaData.breakLevel);
            this.setUpgradeLevel(enkaData.upgradeLevel);
            this.setExp(enkaData.exp);
        }
    }
}
