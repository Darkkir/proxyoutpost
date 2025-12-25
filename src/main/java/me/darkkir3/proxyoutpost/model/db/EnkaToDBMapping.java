package me.darkkir3.proxyoutpost.model.db;

import me.darkkir3.proxyoutpost.model.enka.EnkaData;

/**
 * marker for an entity that is directly mappable to a JSON object from enka api
 * @param <E> the type of data this entity is compatible with2
 */
public interface EnkaToDBMapping<E extends EnkaData> {

    /**
     * fill the data of this entity with the specified JSON pojo
     * @param enkaData the JSON data
     */
    public void mapEnkaDataToDB(E enkaData);
}
