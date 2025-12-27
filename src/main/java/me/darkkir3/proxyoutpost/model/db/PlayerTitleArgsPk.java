package me.darkkir3.proxyoutpost.model.db;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.util.Objects;

@Embeddable
public class PlayerTitleArgsPk {

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

    public PlayerTitleArgsPk() {}

    public PlayerTitleArgsPk(Long profileUid, int argumentIndex) {
        this.profileUid = profileUid;
        this.argumentIndex = argumentIndex;
    }

    public Long getProfileUid() {
        return profileUid;
    }

    public int getArgumentIndex() {
        return argumentIndex;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof PlayerTitleArgsPk that)) return false;
        return argumentIndex == that.argumentIndex && Objects.equals(profileUid, that.profileUid);
    }

    @Override
    public int hashCode() {
        return Objects.hash(profileUid, argumentIndex);
    }
}
