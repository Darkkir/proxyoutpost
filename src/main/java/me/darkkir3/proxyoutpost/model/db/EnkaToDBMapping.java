package me.darkkir3.proxyoutpost.model.db;

import me.darkkir3.proxyoutpost.model.enka.EnkaData;

public interface EnkaToDBMapping<E extends EnkaData> {
    public void mapEnkaDataToDB(E enkaData);
}
