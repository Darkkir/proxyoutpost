package me.darkkir3.proxyoutpost.cache.impl;

import me.darkkir3.proxyoutpost.cache.AbstractEnkaFileCache;
import me.darkkir3.proxyoutpost.cache.EnkaNamecardCache;
import me.darkkir3.proxyoutpost.cache.EnkaStoreType;
import me.darkkir3.proxyoutpost.configuration.EnkaAPIConfiguration;
import me.darkkir3.proxyoutpost.equipment.Namecard;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;
import tools.jackson.databind.JsonNode;
import tools.jackson.databind.ObjectMapper;

@Component
public class DefaultEnkaNamecardCache extends AbstractEnkaFileCache implements EnkaNamecardCache {

    private static final String NAMECARD_CACHE = "NAMECARD_CACHE";
    private static final Logger log = LoggerFactory.getLogger(DefaultEnkaNamecardCache.class);


    public DefaultEnkaNamecardCache(
            EnkaAPIConfiguration enkaAPIConfiguration,
            CacheManager cacheManager) {
        super(enkaAPIConfiguration, cacheManager);
    }

    @Override
    public EnkaStoreType getStoreType() {
        return EnkaStoreType.NAMECARDS;
    }

    @Override
    public String getCacheName() {
        return NAMECARD_CACHE;
    }

    /**
     * grab the namecard by id
     * @param id the id of the namecard to fetch
     * @return the translated namecard instance
     */
    @Cacheable(value= NAMECARD_CACHE)
    @Override
    public Namecard getNamecardById(Long id) {
        JsonNode rootNode = this.getRootNode();
        if(rootNode != null) {
            JsonNode weaponNode = rootNode.get(String.valueOf(id));
            if(weaponNode != null) {
                ObjectMapper objectMapper = new ObjectMapper();
                Namecard namecard = objectMapper.treeToValue(weaponNode, Namecard.class);
                if(namecard != null) {
                    namecard.iconUrl = enkaAPIConfiguration.getBaseUrl() + namecard.iconUrl;
                    return namecard;
                }
                else {
                    log.error("Failed to parse {} for namecard id {}", this.getStoreName(), id);
                }
            }
            else {
                log.error("Could not find namecard with id {}", id);

            }
        }

        return null;
    }
}
