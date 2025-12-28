package me.darkkir3.proxyoutpost.cache.impl;

import me.darkkir3.proxyoutpost.cache.EnkaLocalizationCache;
import me.darkkir3.proxyoutpost.cache.EnkaSuitCache;
import me.darkkir3.proxyoutpost.configuration.EnkaAPIConfiguration;
import me.darkkir3.proxyoutpost.equipment.DriveDiscSuit;
import org.apache.commons.lang3.StringUtils;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

import java.util.HashMap;

@Component
public class DefaultEnkaSuitCache implements EnkaSuitCache {

    private static final String DRIVE_DISC_SUIT_CACHE = "DRIVE_DISC_SUIT_CACHE";


    private final EnkaLocalizationCache enkaLocalizationCache;
    private final EnkaAPIConfiguration enkaAPIConfiguration;

    /**
     * maps suits based on their set name
     */
    private final HashMap<String, DriveDiscSuit> driveDiscSuits;

    public DefaultEnkaSuitCache(EnkaLocalizationCache enkaLocalizationCache, EnkaAPIConfiguration enkaAPIConfiguration) {
        this.enkaLocalizationCache = enkaLocalizationCache;
        this.enkaAPIConfiguration = enkaAPIConfiguration;

        this.driveDiscSuits = new HashMap<>();
    }


    @Cacheable(value = DRIVE_DISC_SUIT_CACHE)
    public DriveDiscSuit getSuitByName(String language, String name) {
        DriveDiscSuit result = this.driveDiscSuits.get(name);

        if(result != null) {
            DriveDiscSuit translatedResult = new DriveDiscSuit();
            if(!StringUtils.isBlank(result.getName())) {
                translatedResult.setName(enkaLocalizationCache.translate(language, result.getName()));
            }

            if(!StringUtils.isBlank(result.getIcon())) {
                translatedResult.setIcon(enkaAPIConfiguration.getBaseUrl() + result.getIcon());
            }

            translatedResult.setSetBonusProps(result.getSetBonusProps());
            return translatedResult;
        }

        return null;
    }

    @Override
    public void registerDriveDiscSuit(DriveDiscSuit driveDiscSuit) {
        this.driveDiscSuits.put(driveDiscSuit.getName(), driveDiscSuit);
    }


    @CacheEvict(value = DRIVE_DISC_SUIT_CACHE, allEntries = true)
    @Override
    public void clearCache() {
    this.driveDiscSuits.clear();
    }
}
