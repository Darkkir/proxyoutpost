package me.darkkir3.proxyoutpost.cache.enka;

import me.darkkir3.proxyoutpost.model.output.MedalOutput;

/**
 * Caches titles.json data
 */
public interface EnkaMedalCache {

    /**
     * fetch an MedalOutput pojo based on the medal id
     * @param id the id of the medal to fetch
     * @return an MedalOutput instance
     */
    public MedalOutput getMedalById(String language, Long id);
}
