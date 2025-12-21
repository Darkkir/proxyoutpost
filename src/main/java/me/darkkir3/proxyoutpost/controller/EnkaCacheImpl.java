package me.darkkir3.proxyoutpost.controller;

import me.darkkir3.proxyoutpost.configuration.EnkaAPIConfiguration;
import me.darkkir3.proxyoutpost.model.db.Profile;
import me.darkkir3.proxyoutpost.model.enka.ZZZProfile;
import me.darkkir3.proxyoutpost.rep.ProfileRepository;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import java.util.HashMap;
import java.util.Optional;

@Component
public class EnkaCacheImpl implements EnkaCache {

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

    public EnkaCacheImpl(ProfileRepository profileRepository, EnkaAPIConfiguration enkaAPIConfiguration) {
        this.profileRepository = profileRepository;
        this.enkaAPIConfiguration = enkaAPIConfiguration;
        this.restClient = RestClient.builder()
                .baseUrl(this.enkaAPIConfiguration.getApiUrl() + this.enkaAPIConfiguration.getUidEndpoint())
                .defaultHeader("User-Agent", this.enkaAPIConfiguration.getUserAgent()).build();
        profileCache = new HashMap<>();
    }

    /**Try to get a entity either from cache or directly from api
     * @param uid the uid of the profile
     * @return
     */
    @Override
    public Profile getProfileByUid(Long uid) {
        Profile cachedProfile = this.profileCache.get(uid);
        if(cachedProfile == null || cachedProfile.isExpired()) {
            cachedProfile = this.fetchNewEnkaProfile(uid);
            this.profileCache.put(uid, cachedProfile);
        }

        return cachedProfile;
    }

    /**
     * fetch an enka profile directly from api or db
     * and save it to db while deleting any previous entries
     * @param uid the uid of the profile
     * @return the db entity
     */
    private Profile fetchNewEnkaProfile(Long uid) {
        Optional<Profile> existingProfile = profileRepository.findById(uid);

        if(existingProfile.isPresent()) {
            Profile p = existingProfile.get();
            if(!p.isExpired()) {
                return p;
            }
            else {
                profileRepository.delete(p);
            }
        }

        ZZZProfile jsonProfile = restClient.get().uri(String.valueOf(uid)).retrieve().body(ZZZProfile.class);

        Profile dbProfile = new Profile();
        dbProfile.mapEnkaDataToDB(jsonProfile);
        profileRepository.save(dbProfile);

        return dbProfile;
    }
}
