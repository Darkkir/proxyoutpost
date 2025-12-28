package me.darkkir3.proxyoutpost.cache.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import me.darkkir3.proxyoutpost.cache.EnkaNamecardCache;
import me.darkkir3.proxyoutpost.cache.EnkaProfileCache;
import me.darkkir3.proxyoutpost.configuration.EnkaAPIConfiguration;
import me.darkkir3.proxyoutpost.equipment.Namecard;
import me.darkkir3.proxyoutpost.model.db.PlayerProfile;
import me.darkkir3.proxyoutpost.model.db.SkillType;
import me.darkkir3.proxyoutpost.model.enka.ZZZProfile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import java.util.HashMap;

@Component
public class DefaultEnkaProfileCache implements EnkaProfileCache {

    private static final Logger log = LoggerFactory.getLogger(DefaultEnkaProfileCache.class);
    /**
     * maps profile uids to the actual profile for caching
     */
    private final HashMap<Long, PlayerProfile> profileCache;

    /**
     * configuration class for enka properties
     */
    private final EnkaAPIConfiguration enkaAPIConfiguration;

    /**
     * enka namecard cache to translate namecard ids into image urls
     */
    private final EnkaNamecardCache enkaNamecardCache;

    /**
     * restClient for calls to enka api
     */
    private final RestClient restClient;

    /**
     * system.nanoTime() of when we last grabbed any profile in any language
     */
    private long timeSinceLastCacheUpdate;

    @PersistenceContext
    EntityManager entityManager;

    public DefaultEnkaProfileCache(EnkaAPIConfiguration enkaAPIConfiguration, EnkaNamecardCache enkaNamecardCache) {
        this.enkaAPIConfiguration = enkaAPIConfiguration;
        this.enkaNamecardCache = enkaNamecardCache;
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
    public PlayerProfile getProfileByUid(String language, Long uid) {
        long currentTime = System.nanoTime();
        double elapsedTimeInSeconds = (currentTime - timeSinceLastCacheUpdate) / 1_000_000_000.0;
        if(elapsedTimeInSeconds > this.enkaAPIConfiguration.getCacheTimeInSeconds()) {
            this.removeExpiredProfiles();
            this.timeSinceLastCacheUpdate = currentTime;
        }

        PlayerProfile cachedPlayerProfile = this.profileCache.get(uid);
        if(cachedPlayerProfile == null || cachedPlayerProfile.isExpired(enkaAPIConfiguration.getMinTtlInSeconds())) {
            cachedPlayerProfile = this.fetchNewEnkaProfile(uid);
            this.profileCache.put(uid, cachedPlayerProfile);
        }

        //translate the namecard url
        if(cachedPlayerProfile.getCallingCardId() != null) {
            Namecard namecardToDisplay = this.enkaNamecardCache.getNamecardById(
                    cachedPlayerProfile.getCallingCardId());
            cachedPlayerProfile.setCallingCardUrl(namecardToDisplay.iconUrl);
        }

        return cachedPlayerProfile;
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
    private PlayerProfile fetchNewEnkaProfile(Long uid) {
        log.info("Trying to fetch a new enka profile: {}", uid);
        PlayerProfile existingProfile = entityManager.find(PlayerProfile.class, uid);

        if(existingProfile != null) {
            if(!existingProfile.isExpired(enkaAPIConfiguration.getMinTtlInSeconds())) {
                log.info("Returning enka profile with uid {} from db", uid);
                return existingProfile;
            }
            else {
                log.info("Found expired profile for uid {}, deleting from database", uid);
                entityManager.remove(existingProfile);
            }
        }

        ZZZProfile jsonProfile = restClient.get().uri(String.valueOf(uid)).retrieve().body(ZZZProfile.class);

        PlayerProfile dbPlayerProfile = new PlayerProfile();
        dbPlayerProfile.mapEnkaDataToDB(jsonProfile);

        //update skill levels based on mindscape level of the agent
        if(dbPlayerProfile.getAgentsList() != null) {
            dbPlayerProfile.getAgentsList().forEach(t -> {
                int mindscapeLevel = t.getMindscapeLevel();
                if(mindscapeLevel >= 0 && mindscapeLevel < enkaAPIConfiguration.getMindScapeSkillLevelOffset().size()) {
                    int offset = enkaAPIConfiguration.getMindScapeSkillLevelOffset().get(mindscapeLevel);
                    if(t.getSkillLevelList() != null) {
                        t.getSkillLevelList().forEach(s -> {
                            //increase every skill except core
                            if(s.getSkillLevelPk() != null
                                    && s.getSkillLevelPk().getSkillIndex() != SkillType.CORE_SKILL.getIndex()) {
                                s.setLevel(s.getLevel() + offset);
                            }
                        });
                    }
                }
            });
        }

        entityManager.persist(dbPlayerProfile);
        log.info("Saving profile with uid {} to db", uid);

        return dbPlayerProfile;
    }
}
