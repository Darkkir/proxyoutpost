package me.darkkir3.proxyoutpost.cache;

import me.darkkir3.proxyoutpost.model.output.WeaponOutput;

/**
 * Caches weapon json data
 */
public interface EnkaWeaponCache {

    /**
     * fetch an WeaponOutput pojo based on the weapon id
     * @param id the id of the weapon to fetch
     * @return an WeaponOutput instance
     */
    public WeaponOutput getWeaponById(String language, Long id);
}
