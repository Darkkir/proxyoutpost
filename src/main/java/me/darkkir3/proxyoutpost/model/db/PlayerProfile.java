package me.darkkir3.proxyoutpost.model.db;

import jakarta.persistence.*;
import me.darkkir3.proxyoutpost.model.enka.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "profiles")
public class PlayerProfile implements EnkaToDBMapping<ZZZProfile> {

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
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "playerProfile")
    @Column(name="titleArgs")
    private List<PlayerTitleArgs> playerTitleArgs;

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

    /**
     * a timestamp of when we created this db entry
     */
    @Column(name="tsCreation")
    private LocalDateTime tsCreation;

    /**
     * the time to live in seconds for this profile (fetched from api)
     */
    @Column(name="ttl")
    private Long ttl;

    /**
     * a list with all agents associated with this profile
     */
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "playerProfile")
    @Column(name="agentsList")
    private List<PlayerAgent> agentsList;

    /**
     * a list with all medals associated with this profile
     */
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "playerProfile")
    @Column(name="medalsList")
    private List<PlayerMedal> playerMedalList;

    public PlayerProfile() {
        this.tsCreation = LocalDateTime.now();
    }

    public PlayerProfile(Long profileUid) {
        this.profileUid = profileUid;
        this();
    }

    public Long getProfileUid() {
        return profileUid;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public Long getAvatarId() {
        return avatarId;
    }

    public void setAvatarId(Long avatarId) {
        this.avatarId = avatarId;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public Long getTitle() {
        return title;
    }

    public void setTitle(Long title) {
        this.title = title;
    }

    public Long getFullTitle() {
        return fullTitle;
    }

    public void setFullTitle(Long fullTitle) {
        this.fullTitle = fullTitle;
    }

    public List<PlayerTitleArgs> getTitleArgs() {
        return playerTitleArgs;
    }

    public void setTitleArgs(List<PlayerTitleArgs> playerTitleArgs) {
        this.playerTitleArgs = playerTitleArgs;
    }

    public Long getProfileId() {
        return profileId;
    }

    public void setProfileId(Long profileId) {
        this.profileId = profileId;
    }

    public Long getCallingCardId() {
        return callingCardId;
    }

    public void setCallingCardId(Long callingCardId) {
        this.callingCardId = callingCardId;
    }

    public int getPlatformType() {
        return platformType;
    }

    public void setPlatformType(int platformType) {
        this.platformType = platformType;
    }

    public List<PlayerAgent> getAgentsList() {
        return agentsList;
    }

    public void setAgentsList(List<PlayerAgent> agentsList) {
        this.agentsList = agentsList;
    }

    public List<PlayerMedal> getMedalList() {
        return playerMedalList;
    }

    public void setMedalList(List<PlayerMedal> playerMedalList) {
        this.playerMedalList = playerMedalList;
    }

    public LocalDateTime getTsCreation() {
        return tsCreation;
    }

    public void setTsCreation(LocalDateTime tsCreation) {
        this.tsCreation = tsCreation;
    }

    public Long getTtl() {
        return ttl;
    }

    public void setTtl(Long ttl) {
        this.ttl = ttl;
    }

    /**
     * @return a flag that indicates whether this object exceeded the ttl
     */
    public boolean isExpired() {
        return this.isExpired(0);
    }

    /**
     * @param minTtl the minimum amount of time in seconds this object is considered not expired
     * @return a flag that indicates whether this object exceeded the ttl
     */
    public boolean isExpired(int minTtl) {
        return this.tsCreation != null
                && this.ttl != null
                && LocalDateTime.now().isAfter(this.tsCreation.plusSeconds(Math.max(this.ttl, minTtl)));
    }

    @Override
    public void mapEnkaDataToDB(ZZZProfile enkaData) {
        if(enkaData != null) {
            this.profileUid = enkaData.uid;
            this.setTtl(enkaData.ttl);
            if(enkaData.playerInfo != null) {
                PlayerInfo playerInfo = enkaData.playerInfo;
                this.convertSocialDetail(playerInfo);
                this.convertShowcaseDetail(playerInfo);
            }
        }
    }

    private void convertShowcaseDetail(PlayerInfo playerInfo) {
        if(playerInfo.showcaseDetail != null) {
            List<AvatarList> avatarList = playerInfo.showcaseDetail.avatarList;
            if(avatarList != null) {
                List<PlayerAgent> playerAgents = new ArrayList<>();
                avatarList.forEach(t -> {
                    PlayerAgent playerAgent = new PlayerAgent(new PlayerAgentPk(this.getProfileUid(), t.id));
                    playerAgent.mapEnkaDataToDB(t);
                    playerAgents.add(playerAgent);
                });
                this.setAgentsList(playerAgents);
            }
        }
    }

    private void convertSocialDetail(PlayerInfo playerInfo) {
        if(playerInfo.socialDetail != null) {
            SocialDetail socialDetail = playerInfo.socialDetail;
            this.setDescription(socialDetail.desc);
            if(socialDetail.medalList != null) {
                List<PlayerMedal> playerMedals = new ArrayList<>();
                for(int i = 0; i < socialDetail.medalList.size(); i++) {
                    MedalList jsonMedal = socialDetail.medalList.get(i);
                    PlayerMedal playerMedal = new PlayerMedal(new PlayerMedalPk(this.getProfileUid(), i));
                    playerMedal.mapEnkaDataToDB(jsonMedal);
                    playerMedals.add(playerMedal);
                }
                this.setMedalList(playerMedals);
            }
            this.convertProfileDetail(socialDetail);
        }
    }

    private void convertProfileDetail(SocialDetail socialDetail) {
        if(socialDetail.profileDetail != null) {
            ProfileDetail profileDetail = socialDetail.profileDetail;
            this.setNickname(profileDetail.nickname);
            this.setAvatarId(profileDetail.avatarId);
            this.setLevel(profileDetail.level);
            this.setTitle(profileDetail.title);
            this.setProfileId(profileDetail.profileId);
            this.setPlatformType(profileDetail.platformType);
            this.setCallingCardId(profileDetail.callingCardId);
            this.convertTitleInfo(profileDetail);
        }
    }

    private void convertTitleInfo(ProfileDetail profileDetail) {
        if(profileDetail.titleInfo != null) {
            TitleInfo titleInfo = profileDetail.titleInfo;
            this.setTitle(titleInfo.title);
            this.setFullTitle(titleInfo.fullTitle);
            if(titleInfo.args != null && !titleInfo.args.isEmpty()) {
                List<PlayerTitleArgs> titleInfoArgs = new ArrayList<>();
                for (int i = 0; i < titleInfo.args.size(); i++) {
                    PlayerTitleArgs newArg = new PlayerTitleArgs(new PlayerTitleArgsPk(this.profileUid, i));
                    newArg.mapEnkaDataToDB(titleInfo);
                    titleInfoArgs.add(newArg);
                }
                this.setTitleArgs(titleInfoArgs);
            }
        }
    }
}
