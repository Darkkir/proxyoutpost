package me.darkkir3.proxyoutpost.model.db;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.util.Objects;

@Embeddable
public class PlayerMedalPk {

    /**
     * the profile uid this medal belongs to
     */
    @Column(name="profileUid", nullable = false)
    private Long profileUid;
    /**
     * the display index of this medal
     */
    @Column(name="medalIndex", nullable = false)
    private int medalIndex;

    public PlayerMedalPk() {}

    public PlayerMedalPk(Long profileUid, int medalIndex) {
        this.profileUid = profileUid;
        this.medalIndex = medalIndex;
    }

    public Long getProfileUid() {
        return profileUid;
    }

    public int getMedalIndex() {
        return medalIndex;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof PlayerMedalPk that)) return false;
        return medalIndex == that.medalIndex && Objects.equals(profileUid, that.profileUid);
    }

    @Override
    public int hashCode() {
        return Objects.hash(profileUid, medalIndex);
    }
}
