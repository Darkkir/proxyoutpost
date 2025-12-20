package me.darkkir3.proxyoutpost.model.db;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

@Embeddable
public class TitleArgsPk {

    /**
     * the uid of the profile this title argument belongs to
     */
    @Column(name="profileUid", nullable = false)
    private Long profileUid;

    /**
     * the index of this argument in the argument list
     */
    @Column(name="argumentIndex", nullable = false)
    private int argumentIndex;

    public TitleArgsPk() {}

    public TitleArgsPk(Long profileUid, int argumentIndex) {
        this.profileUid = profileUid;
        this.argumentIndex = argumentIndex;
    }

    public Long getProfileUid() {
        return profileUid;
    }

    public int getArgumentIndex() {
        return argumentIndex;
    }
}
