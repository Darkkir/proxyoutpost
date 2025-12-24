package me.darkkir3.proxyoutpost.cache;

import me.darkkir3.proxyoutpost.model.output.PropertyOutput;

/**
 * Caches agent json data
 */
public interface EnkaPropertyCache {

    /**
     * fetch an PropertyOutput pojo based on the property id
     * @param id the id of the property to fetch
     * @return an PropertyOutput instance
     */
    public PropertyOutput getPropertyById(String language, Long id);
}
