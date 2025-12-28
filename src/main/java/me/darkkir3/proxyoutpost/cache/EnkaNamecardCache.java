package me.darkkir3.proxyoutpost.cache;

import me.darkkir3.proxyoutpost.equipment.Namecard;

/**
 * Caches player namecard instances based on their id
 */
public interface EnkaNamecardCache {

    /**
     * try and fetch a namecard based on the passed id
     * @param id the id of the namecard
     * @return a valid namecard instance
     */
    public Namecard getNamecardById(Long id);
}
