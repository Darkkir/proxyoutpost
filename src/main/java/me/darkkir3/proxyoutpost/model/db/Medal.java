package me.darkkir3.proxyoutpost.model.db;

import jakarta.persistence.*;

@Entity
@Table(name="medals")
public class Medal {

    /**
     * primary key of medal
     */
    @EmbeddedId
    private MedalPk medalPk;

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
    private Profile profile;

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

    public MedalPk getMedalPk() {
        return medalPk;
    }

    public Profile getProfile() {
        return profile;
    }
}
