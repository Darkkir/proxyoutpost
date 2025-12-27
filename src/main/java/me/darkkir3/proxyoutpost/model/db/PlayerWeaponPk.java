package me.darkkir3.proxyoutpost.model.db;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class PlayerWeaponPk implements Serializable {
    /**
     * the uid of the profile this belongs to
     */
    @Column(name="profileUid", nullable = false)
    private Long profileUid;
    /**
     * id of the agent this weapon belongs to
     */
    @Column(name="agentId", nullable = false)
    private Long agentId;
    /**
     * the unique weapon id
     */
    @Column(name="weaponUid", nullable = false)
    private Long weaponUid;

    public PlayerWeaponPk() {}

    public PlayerWeaponPk(Long profileUid, Long agentId, Long weaponUid) {
        this.profileUid = profileUid;
        this.agentId = agentId;
        this.weaponUid = weaponUid;
    }

    public Long getProfileUid() {
        return profileUid;
    }

    public Long getAgentId() {
        return agentId;
    }

    public Long getWeaponUid() {
        return weaponUid;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof PlayerWeaponPk that)) return false;
        return Objects.equals(profileUid, that.profileUid) && Objects.equals(agentId, that.agentId) && Objects.equals(weaponUid, that.weaponUid);
    }

    @Override
    public int hashCode() {
        return Objects.hash(profileUid, agentId, weaponUid);
    }
}
