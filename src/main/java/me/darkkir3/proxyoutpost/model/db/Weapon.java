package me.darkkir3.proxyoutpost.model.db;

import jakarta.persistence.*;

@Entity
@Table(name="weapons")
public class Weapon {

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
}
