package me.darkkir3.proxyoutpost.cache;

import me.darkkir3.proxyoutpost.model.db.PlayerProfile;

/**
 * Caches player profile requests by uid
 */
public interface EnkaProfileCache {

    /**
     * try and fetch a profile based on the passed uid
     * @param uid the uid of the profile
     * @return a valid profile instance
     */
    public PlayerProfile getProfileByUid(String language, Long uid);
}
