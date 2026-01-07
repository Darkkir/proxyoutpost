package me.darkkir3.proxyoutpost.cache.enka;

import me.darkkir3.proxyoutpost.equipment.DriveDiscSuit;

/**
 * Caches drive disc suit data
 */
public interface EnkaSuitCache {

    /**
     * fetch an DriveDiscSuit pojo while translating the set name and icon url
     * @param id the id of the agent to fetch
     * @return an DriveDiscSuit instance
     */
    public DriveDiscSuit getSuitByName(String language, String id);
    public void registerDriveDiscSuit(DriveDiscSuit driveDiscSuit);
    public void clearCache();
}
