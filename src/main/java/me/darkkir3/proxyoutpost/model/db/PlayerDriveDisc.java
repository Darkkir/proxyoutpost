package me.darkkir3.proxyoutpost.model.db;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonUnwrapped;
import jakarta.persistence.*;
import me.darkkir3.proxyoutpost.equipment.DriveDiscSuit;
import me.darkkir3.proxyoutpost.model.enka.Equipment;
import me.darkkir3.proxyoutpost.model.enka.EquippedList;
import me.darkkir3.proxyoutpost.model.enka.PropertyEntry;

import java.util.*;

@Entity
@Table(name="drive_discs")
public class PlayerDriveDisc implements EnkaToDBMapping<EquippedList> {

    /**
     * the slot this is equipped on
     */
    @EmbeddedId
    private PlayerDriveDiscPk playerDriveDiscPk;

    /**
     * the untranslated name of the drive disc set this belongs to
     */
    @Column(name="setName")
    private String setName;

    /**
     * the id of the drive disc set this belongs to
     */
    @Column(name="setId")
    private long setId;

    /**
     * the actual level of the drive disc (max=15)
     */
    @Column(name="level")
    private int level;

    /**
     * the unique id of this drive disc (differs between profiles)
     */
    @Column(name="uid")
    private int uid;

    /**
     * the break level (how often has a stat been upgraded)
     */
    @Column(name="breakLevel")
    private int breakLevel;

    /**
     * the rarity of this drive disc (4=S-RANK)
     */
    @Column(name="rarity")
    private int rarity;

    /**
     * the actual suit (set) of this drive disc
     */
    @Transient
    private DriveDiscSuit driveDiscSuit;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "playerDriveDisc")
    @Column(name="driveDiscList")
    private List<PlayerDriveDiscProperty> driveDiscProperties;

    /**
     * the playerAgent this drive disc belongs to
     */
    @JsonIgnore
    @ManyToOne
    @JoinColumn(name="profileUid", referencedColumnName = "profileUid", insertable = false, updatable = false)
    @JoinColumn(name="agentId", referencedColumnName = "agentId", insertable = false, updatable = false)
    private PlayerAgent playerAgent;

    public PlayerDriveDisc() {}

    public PlayerDriveDisc(PlayerDriveDiscPk playerDriveDiscPk) {
        this.playerDriveDiscPk = playerDriveDiscPk;
    }

    @JsonUnwrapped
    public PlayerDriveDiscPk getPlayerDriveDiscPk() {
        return playerDriveDiscPk;
    }

    @JsonIgnore
    public PlayerAgent getAgent() {
        return playerAgent;
    }

    /**
     * @return the untranslated name of the drive disc set this belongs to
     */
    @JsonIgnore
    public String getSetName() {
        return setName;
    }

    /**
     * set the untranslated name of the drive disc set this belongs to
     * @param setName
     */
    public void setSetName(String setName) {
        this.setName = setName;
    }

    @JsonIgnore
    public long getSetId() {
        return setId;
    }

    public void setSetId(long setId) {
        this.setId = setId;
    }

    @JsonProperty("Level")
    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    @JsonProperty("BreakLevel")
    public int getBreakLevel() {
        return breakLevel;
    }

    public void setBreakLevel(int breakLevel) {
        this.breakLevel = breakLevel;
    }

    @JsonProperty("Uid")
    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    @JsonProperty("Rarity")
    public String getRarity() {
        Optional<DriveDiscRarity> driveDisc = Arrays.stream(DriveDiscRarity.values()).filter(t ->
                Objects.equals(this.rarity, t.getIndex())).findFirst();

        return driveDisc.map(Enum::name).orElse(null);
    }

    @JsonIgnore
    public int getRarityAsInt() {
        return rarity;
    }

    @JsonIgnore
    public void setRarity(int rarity) {
        this.rarity = rarity;
    }

    /**
     * @return all secondary stats for this drive disc
     */
    @JsonProperty("SecondaryProperties")
    public List<PlayerDriveDiscProperty> getSecondaryProperties() {
        if(driveDiscProperties == null) {
            return Collections.emptyList();
        }

        return driveDiscProperties.stream().filter(t ->
                !t.getPlayerDriveDiscPropertyPk().isMainStat())
                .toList();
    }

    /**
     * @return the main stat property for this drive disc if it has been set
     */
    @JsonProperty("MainProperty")
    public PlayerDriveDiscProperty getMainProperty() {
        if(driveDiscProperties == null) {
            return null;
        }

        return driveDiscProperties.stream().filter(t ->
                                t.getPlayerDriveDiscPropertyPk().isMainStat())
                .findAny().orElse(null);
    }

    /**
     * @return the translated property name
     */
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonUnwrapped
    public DriveDiscSuit getDriveDiscSuit() {
        return this.driveDiscSuit;
    }

    /**
     * set the translated property value
     */
    public void setDriveDiscSuit(DriveDiscSuit value) {
        this.driveDiscSuit = value;
    }

    @Override
    public void mapEnkaDataToDB(EquippedList enkaData) {
        if(enkaData.equipment != null)  {
            Equipment equipment = enkaData.equipment;
            this.setId = equipment.id;
            this.level = equipment.level;
            this.uid = equipment.uid;
            this.breakLevel = equipment.breakLevel;

            this.driveDiscProperties = new ArrayList<>();
            if(equipment.mainPropertyList != null) {
                List<PropertyEntry> mainEntries = equipment.mainPropertyList;
                addDriveDiscProperties(mainEntries, true);
            }

            if(equipment.secondaryPropertyList != null) {
                List<PropertyEntry> secondaryEntries = equipment.secondaryPropertyList;
                addDriveDiscProperties(secondaryEntries, false);
            }
        }
    }

    private void addDriveDiscProperties(List<PropertyEntry> mainEntries, boolean isMainStat) {
        mainEntries.forEach(t -> {
            PlayerDriveDiscProperty property = new PlayerDriveDiscProperty(
                    new PlayerDriveDiscPropertyPk(
                            this.playerDriveDiscPk.getProfileUid(),
                            this.playerDriveDiscPk.getAgentId(),
                            this.playerDriveDiscPk.getSlot(),
                            t.propertyId,
                            isMainStat));
            property.mapEnkaDataToDB(t);
            this.driveDiscProperties.add(property);
        });
    }
}
