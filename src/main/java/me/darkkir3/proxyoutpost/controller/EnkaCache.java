package me.darkkir3.proxyoutpost.controller;

import me.darkkir3.proxyoutpost.model.db.Profile;

public interface EnkaCache {
    /**
     * try and fetch a profile based on the passed uid
     * @param uid the uid of the profile
     * @return a valid profile instance
     */
    public Profile getProfileByUid(Long uid);
}
