package me.darkkir3.proxyoutpost.model.db;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

@Embeddable
public class MedalPk {

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

    public MedalPk() {}

    public MedalPk(Long profileUid, int medalIndex) {
        this.profileUid = profileUid;
        this.medalIndex = medalIndex;
    }

    public Long getProfileUid() {
        return profileUid;
    }

    public int getMedalIndex() {
        return medalIndex;
    }
}
