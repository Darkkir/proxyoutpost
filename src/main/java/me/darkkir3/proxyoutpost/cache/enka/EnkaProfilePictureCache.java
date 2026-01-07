package me.darkkir3.proxyoutpost.cache.enka;

import me.darkkir3.proxyoutpost.equipment.IconResource;

/**
 * Caches profile picture icon URLs based on their id
 */
public interface EnkaProfilePictureCache {

    /**
     * try and fetch a resource based on the passed id
     * @param id the id of the resource
     * @return a valid IconResource instance
     */
    public IconResource getProfilePictureById(Long id);
}
