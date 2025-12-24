package me.darkkir3.proxyoutpost.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

/**
 * Configuration properties for WeaponLevelTemplateTb.json and WeaponStarTemplateTb.sjon
 */
@Configuration
@PropertySource("classpath:weapons.properties")
public class WeaponsConfiguration {

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

    @Value("${level.url}")
    private String levelUrl;

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
     * @return the url we can grab the level file from if we need to refresh it
     */
    public String getLevelUrl() {
        return levelUrl;
    }
}