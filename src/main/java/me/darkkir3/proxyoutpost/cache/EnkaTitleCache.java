package me.darkkir3.proxyoutpost.cache;

import me.darkkir3.proxyoutpost.model.output.TitleOutput;

/**
 * Caches titles.json data
 */
public interface EnkaTitleCache {

    /**
     * fetch an TitleOutput pojo based on the title id
     * @param id the id of the property to fetch
     * @return an TitleOutput instance
     */
    public TitleOutput getTitleById(String language, Long id);
}
