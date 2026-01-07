package me.darkkir3.proxyoutpost.cache.enka;

import me.darkkir3.proxyoutpost.model.db.PlayerWeapon;
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

    /**
     * Update the mainStat and secondaryStat of the specific player weapon based
     * on the information gathered from the general weapon
     * @param playerWeapon the actual player weapon
     * @param weaponOutput the generic weapon information
     */
    public void updatePlayerWeaponStats(PlayerWeapon playerWeapon, WeaponOutput weaponOutput);
}
