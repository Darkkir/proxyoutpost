package me.darkkir3.proxyoutpost.cache.enka;

import me.darkkir3.proxyoutpost.equipment.IconResource;

/**
 * Caches namecard icon URLs based on their id
 */
public interface EnkaNamecardCache {

    /**
     * try and fetch a resource based on the passed id
     * @param id the id of the resource
     * @return a valid IconResource instance
     */
    public IconResource getNamecardById(Long id);
}
