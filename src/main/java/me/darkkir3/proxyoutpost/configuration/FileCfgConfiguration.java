package me.darkkir3.proxyoutpost.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

/**
 * Configuration properties for WeaponLevelTemplateTb.json and WeaponStarTemplateTb.sjon
 */
@Configuration
@PropertySource("classpath:items.properties")
public class FileCfgConfiguration {

    @Value("${star.rarity}")
    private String starRarity;

    @Value("${star.break-level}")
    private String starBreakLevel;

    @Value("${star.main-stat}")
    private String starMainStat;

    @Value("${star.secondary-stat}")
    private String starSecondaryStat;

    @Value("${star.path}")
    private String starPath;

    @Value("${star.file-name}")
    private String starFileName;

    @Value("${star.url}")
    private String starUrl;

    @Value("${level.rarity}")
    private String levelRarity;

    @Value("${level.level}")
    private String levelLevel;

    @Value("${level.main-stat}")
    private String levelMainStat;

    @Value("${level.path}")
    private String levelPath;

    @Value("${level.file-name}")
    private String levelFileName;

    @Value("${level.url}")
    private String levelUrl;

    @Value("${equipment.rarity}")
    private String equipmentRarity;

    @Value("${equipment.level}")
    private String equipmentLevel;

    @Value("${equipment.main-stat}")
    private String equipmentMainStat;

    @Value("${equipment.path}")
    private String equipmentPath;

    @Value("${equipment.file-name}")
    private String equipmentFileName;

    @Value("${equipment.url}")
    private String equipmentUrl;

    @Value("${drive-disc.path}")
    private String driveDiscPath;

    @Value("${drive-disc.file-name}")
    private String driveDiscFileName;

    @Value("${drive-disc.url}")
    private String driveDiscUrl;

    @Value("${drive-disc.discs-per-set}")
    private int driveDiscDiscsPerSet;

    /**
     * @return the obfuscated key of the rarity field
     */
    public String getStarRarity() {
        return starRarity;
    }

    /**
     * @return the obfuscated key of the break level field
     */
    public String getStarBreakLevel() {
        return starBreakLevel;
    }

    /**
     * @return the obfuscated key of the weapon main stat value
     */
    public String getStarMainStat() {
        return starMainStat;
    }

    /**
     * @return the obfuscated key of the weapon secondary stat value
     */
    public String getStarSecondaryStat() {
        return starSecondaryStat;
    }

    /**
     * @return file path to the WeaponStarTemplateTb.json file
     */
    public String getStarPath() {
        return starPath;
    }

    /**
     * @return name of the WeaponStarTemplateTb.json file
     */
    public String getStarFileName() {
        return starFileName;
    }

    /**
     * @return the url we can grab the star file from if we need to refresh it
     */
    public String getStarUrl() {
        return starUrl;
    }

    /**
     * @return the obfuscated key of the rarity field
     */
    public String getLevelRarity() {
        return levelRarity;
    }

    /**
     * @return the obfuscated key of the weapon level field
     */
    public String getLevelLevel() {
        return levelLevel;
    }

    /**
     * @return the obfuscated key of the weapon main stat value
     */
    public String getLevelMainStat() {
        return levelMainStat;
    }

    /**
     * @return file path to the WeaponLevelTemplateTb.json file
     */
    public String getLevelPath() {
        return levelPath;
    }

    /**
     * @return name of the WeaponLevelTemplateTb.json file
     */
    public String getLevelFileName() {
        return levelFileName;
    }

    /**
     * @return the url we can grab the level file from if we need to refresh it
     */
    public String getLevelUrl() {
        return levelUrl;
    }

    /**
     * @return the obfuscated key of the rarity field
     */
    public String getEquipmentRarity() {
        return equipmentRarity;
    }

    /**
     * @return the obfuscated key of the level field
     */
    public String getEquipmentLevel() {
        return equipmentLevel;
    }

    /**
     * @return the obfuscated key of the equipment main stat
     */
    public String getEquipmentMainStat() {
        return equipmentMainStat;
    }

    /**
     * @return the local folder to store the EquipmentLevelTemplateTb.json config file in
     */
    public String getEquipmentPath() {
        return equipmentPath;
    }

    /**
     * @return the file name of EquipmentLevelTemplateTb.json
     */
    public String getEquipmentFileName() {
        return equipmentFileName;
    }

    /**
     * @return the url we can grab the EquipmentLevelTemplateTb.json from
     */
    public String getEquipmentUrl() {
        return equipmentUrl;
    }

    /**
     * @return the local folder to store equipments.json
     */
    public String getDriveDiscPath() {
        return driveDiscPath;
    }

    /**
     * @return the file name of equipments.json
     */
    public String getDriveDiscFileName() {
        return driveDiscFileName;
    }

    /**
     * @return the url we can grab equipments.json from
     */
    public String getDriveDiscUrl() {
        return driveDiscUrl;
    }

    /**
     * @return how many drive discs of one set/suit
     * do we need to equip on an agent to apply the set bonus
     */
    public int getDriveDiscDiscsPerSet() {
        return driveDiscDiscsPerSet;
    }
}