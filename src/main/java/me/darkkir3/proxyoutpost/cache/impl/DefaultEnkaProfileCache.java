package me.darkkir3.proxyoutpost.cache.impl;

import me.darkkir3.proxyoutpost.cache.EnkaProfileCache;
import me.darkkir3.proxyoutpost.configuration.EnkaAPIConfiguration;
import me.darkkir3.proxyoutpost.model.db.Profile;
import me.darkkir3.proxyoutpost.model.enka.ZZZProfile;
import me.darkkir3.proxyoutpost.rep.ProfileRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import java.util.HashMap;
import java.util.Optional;

@Component
public class DefaultEnkaProfileCache implements EnkaProfileCache {

    private static final Logger log = LoggerFactory.getLogger(DefaultEnkaProfileCache.class);
    /**
     * maps profile uids to the actual profile for caching
     */
    private final HashMap<Long, Profile> profileCache;

    /**
     * profile repository
     */
    private final ProfileRepository profileRepository;

    /**
     * configuration class for enka properties
     */
    private final EnkaAPIConfiguration enkaAPIConfiguration;

    /**
     * restClient for calls to enka api
     */
    private final RestClient restClient;

    private long timeSinceLastCacheUpdate;

    public DefaultEnkaProfileCache(ProfileRepository profileRepository, EnkaAPIConfiguration enkaAPIConfiguration) {
        this.profileRepository = profileRepository;
        this.enkaAPIConfiguration = enkaAPIConfiguration;
        this.restClient = RestClient.builder()
                .baseUrl(this.enkaAPIConfiguration.getApiUrl() + this.enkaAPIConfiguration.getUidEndpoint())
                .defaultHeader("User-Agent", this.enkaAPIConfiguration.getUserAgent()).build();
        profileCache = new HashMap<>();
    }

    /**Try to get an entity either from cache/db or directly from api
     * @param uid the uid of the profile
     * @return the profile
     */
    @Override
    public Profile getProfileByUid(Long uid) {
        long currentTime = System.nanoTime();
        double elapsedTimeInSeconds = (currentTime - timeSinceLastCacheUpdate) / 1_000_000_000.0;
        if(elapsedTimeInSeconds > this.enkaAPIConfiguration.getCacheTimeInSeconds()) {
            this.removeExpiredProfiles();
            this.timeSinceLastCacheUpdate = currentTime;
        }

        Profile cachedProfile = this.profileCache.get(uid);
        if(cachedProfile == null || cachedProfile.isExpired(enkaAPIConfiguration.getMinTtlInSeconds())) {
            cachedProfile = this.fetchNewEnkaProfile(uid);
            this.profileCache.put(uid, cachedProfile);
        }

        return cachedProfile;
    }

    private void removeExpiredProfiles() {
        this.profileCache.entrySet().removeIf(
                t -> t.getValue().isExpired(enkaAPIConfiguration.getMinTtlInSeconds()));
    }

    /**
     * fetch an enka profile directly from api or db
     * and save it to db while deleting any previous entries
     * @param uid the uid of the profile
     * @return the db entity
     */
    private Profile fetchNewEnkaProfile(Long uid) {
        log.info("Trying to fetch a new enka profile: {}", uid);
        Optional<Profile> existingProfile = profileRepository.findById(uid);

        if(existingProfile.isPresent()) {
            Profile p = existingProfile.get();
            if(!p.isExpired(enkaAPIConfiguration.getMinTtlInSeconds())) {
                log.info("Returning enka profile with uid {} from db", uid);
                return p;
            }
            else {
                log.info("Found expired profile for uid {}, deleting from database", uid);
                profileRepository.delete(p);
            }
        }

        ZZZProfile jsonProfile = restClient.get().uri(String.valueOf(uid)).retrieve().body(ZZZProfile.class);

        Profile dbProfile = new Profile();
        dbProfile.mapEnkaDataToDB(jsonProfile);
        profileRepository.save(dbProfile);
        log.info("Saving profile with uid {} to db", uid);

        return dbProfile;
    }
}
