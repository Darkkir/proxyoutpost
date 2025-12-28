package me.darkkir3.proxyoutpost.cache.impl;

import me.darkkir3.proxyoutpost.cache.*;
import me.darkkir3.proxyoutpost.configuration.EnkaAPIConfiguration;
import me.darkkir3.proxyoutpost.equipment.IconResource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;
import tools.jackson.databind.JsonNode;
import tools.jackson.databind.ObjectMapper;

@Component
public class DefaultEnkaProfilePictureCache extends AbstractEnkaFileCache implements EnkaProfilePictureCache {

    private static final String PROFILE_PICTURE_CACHE = "PROFILE_PICTURE_CACHE";
    private static final Logger log = LoggerFactory.getLogger(DefaultEnkaProfilePictureCache.class);


    public DefaultEnkaProfilePictureCache(
            EnkaAPIConfiguration enkaAPIConfiguration,
            CacheManager cacheManager) {
        super(enkaAPIConfiguration, cacheManager);
    }

    @Override
    public EnkaStoreType getStoreType() {
        return EnkaStoreType.PFPS;
    }

    @Override
    public String getCacheName() {
        return PROFILE_PICTURE_CACHE;
    }

    /**
     * grab the profile picture by id
     * @param id the id of the profile picture to fetch
     * @return the translated profile picture instance
     */
    @Cacheable(value = PROFILE_PICTURE_CACHE)
    @Override
    public IconResource getProfilePictureById(Long id) {
        JsonNode rootNode = this.getRootNode();
        if(rootNode != null) {
            JsonNode weaponNode = rootNode.get(String.valueOf(id));
            if(weaponNode != null) {
                ObjectMapper objectMapper = new ObjectMapper();
                IconResource iconResource = objectMapper.treeToValue(weaponNode, IconResource.class);
                if(iconResource != null) {
                    iconResource.iconUrl = enkaAPIConfiguration.getBaseUrl() + iconResource.iconUrl;
                    return iconResource;
                }
                else {
                    log.error("Failed to parse {} for profile picture id {}", this.getStoreName(), id);
                }
            }
            else {
                log.error("Could not find profile picture with id {}", id);

            }
        }

        return null;
    }
}
