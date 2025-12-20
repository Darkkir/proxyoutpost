package me.darkkir3.proxyoutpost.model.db;

import jakarta.persistence.*;
import me.darkkir3.proxyoutpost.model.enka.ZZZProfile;

import java.util.List;

@Entity
@Table(name = "profiles")
public class Profile implements EnkaToDBMapping<ZZZProfile> {
    @Id
    @Column(name="profileUid", nullable = false)
    private Long profileUid;

    /**
     * the ingame description of this profile
     */
    @Column(name="description")
    private String description;

    /**
     * short name of this profile
     */
    @Column(name="nickname")
    private String nickname;

    /**
     * id of the main character of this profile (Wise or Belle)
     */
    @Column(name="avatarId")
    private Long avatarId;

    /**
     * the inter-knot level of this profile
     */
    @Column(name="level")
    private int level;

    /**
     * title id
     */
    @Column(name="title")
    private Long title;

    /**
     * full title id used to specify title variants
     */
    @Column(name="fullTitle")
    private Long fullTitle;

    /**
     * the title arguments for the currently set title
     */
    @OneToMany(mappedBy = "profile")
    private List<TitleArgs> titleArgs;

    /**
     * image id of the profile picture
     */
    @Column(name="profileId")
    private Long profileId;

    /**
     * image id of the profile namecard
     */
    @Column(name="callingCardId")
    private Long callingCardId;

    /**
     * which platform this profile is associated with (3=PC)
     */
    @Column(name="platformType")
    private int platformType;

    @OneToMany(mappedBy = "profile")
    private List<Agent> agentsList;

    @OneToMany(mappedBy = "profile")
    private List<Medal> medalList;

    public Long getProfileUid() {
        return profileUid;
    }

    public void setProfileUid(Long profileUid) {
        this.profileUid = profileUid;
    }

    @Override
    public void mapEnkaDataToDB(ZZZProfile enkaData) {
        if(enkaData != null) {
            this.profileUid = Long.valueOf(enkaData.uid);

        }
    }
}
