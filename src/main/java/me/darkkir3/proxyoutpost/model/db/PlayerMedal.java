package me.darkkir3.proxyoutpost.model.db;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import me.darkkir3.proxyoutpost.model.enka.MedalList;

@Entity
@Table(name="medals")
public class PlayerMedal implements EnkaToDBMapping<MedalList> {

    /**
     * primary key of medal
     */
    @EmbeddedId
    private PlayerMedalPk playerMedalPk;

    /**
     * the progress value of this medal (seems to be a duplicate of medal score?)
     */
    @Column(name="value")
    private int value;

    /**
     * the progress value of this medal
     */
    @Column(name="medalScore")
    private int medalScore;

    /**
     * the icon id of this medal
     */
    @Column(name="medalIcon")
    private int medalIcon;

    /**
     * the badge type
     * <li>1=shiyu defense</li>
     * <li>2=simulated battle tower</li>
     * <li>3=deadly assault</li>
     * <li>4=simulated battle tower: last stand</li>
     * <li>5=deadly assault: disintegration</li>
     * <li>6=shiyu defense: lingering sun</li>
     */
    @Column(name="medalType")
    private int medalType;

    @ManyToOne
    @JoinColumn(name="profileUid", referencedColumnName = "profileUid", insertable = false, updatable = false)
    private PlayerProfile playerProfile;

    public PlayerMedal(){}

    public PlayerMedal(PlayerMedalPk playerMedalPk) {
        this.playerMedalPk = playerMedalPk;
    }

    @JsonIgnore
    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public int getMedalScore() {
        return medalScore;
    }

    public void setMedalScore(int medalScore) {
        this.medalScore = medalScore;
    }

    public int getMedalIcon() {
        return medalIcon;
    }

    public void setMedalIcon(int medalIcon) {
        this.medalIcon = medalIcon;
    }

    public int getMedalType() {
        return medalType;
    }

    public void setMedalType(int medalType) {
        this.medalType = medalType;
    }

    @JsonIgnore
    public PlayerMedalPk getMedalPk() {
        return playerMedalPk;
    }

    @JsonIgnore
    public PlayerProfile getProfile() {
        return playerProfile;
    }

    @Override
    public void mapEnkaDataToDB(MedalList enkaData) {
        if(this.playerMedalPk != null && enkaData != null) {
            this.setValue(enkaData.value);
            this.setMedalScore(enkaData.medalScore);
            this.setMedalIcon(enkaData.medalIcon);
            this.setMedalType(enkaData.medalType);
        }
    }
}
