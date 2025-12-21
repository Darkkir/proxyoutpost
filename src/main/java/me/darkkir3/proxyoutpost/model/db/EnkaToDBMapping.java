package me.darkkir3.proxyoutpost.model.db;

import me.darkkir3.proxyoutpost.model.enka.EnkaData;

public interface EnkaToDBMapping<E extends EnkaData> {

    /**
     * fill the data of this entity with the specified JSON pojo
     * @param enkaData the JSON data
     */
    public void mapEnkaDataToDB(E enkaData);
}
