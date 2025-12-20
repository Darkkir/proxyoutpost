package me.darkkir3.proxyoutpost.model.db;

import jakarta.persistence.*;

@Entity
@Table(name="weapons")
public class Weapon implements EnkaToDBMapping<me.darkkir3.proxyoutpost.model.enka.Weapon> {

    /**
     * primary key of weapon
     */
    @EmbeddedId
    private WeaponPk weaponPk;

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

    @OneToOne
    @JoinColumns({
            @JoinColumn(name="profileUid", referencedColumnName = "profileUid", insertable = false, updatable = false),
            @JoinColumn(name="agentId", referencedColumnName = "agentId", insertable = false, updatable = false),
    })
    private Agent agent;

    public Weapon() {}

    public Weapon(WeaponPk weaponPk) {
        this.weaponPk = weaponPk;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public void setAvailable(boolean available) {
        isAvailable = available;
    }

    public boolean isLocked() {
        return isLocked;
    }

    public void setLocked(boolean locked) {
        isLocked = locked;
    }

    public Long getWeaponId() {
        return weaponId;
    }

    public void setWeaponId(Long weaponId) {
        this.weaponId = weaponId;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getBreakLevel() {
        return breakLevel;
    }

    public void setBreakLevel(int breakLevel) {
        this.breakLevel = breakLevel;
    }

    public int getUpgradeLevel() {
        return upgradeLevel;
    }

    public void setUpgradeLevel(int upgradeLevel) {
        this.upgradeLevel = upgradeLevel;
    }

    public int getExp() {
        return exp;
    }

    public void setExp(int exp) {
        this.exp = exp;
    }

    public Agent getAgent() {
        return agent;
    }

    @Override
    public void mapEnkaDataToDB(me.darkkir3.proxyoutpost.model.enka.Weapon enkaData) {
        if(this.weaponPk != null && enkaData != null) {
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
