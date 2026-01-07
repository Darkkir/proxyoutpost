package me.darkkir3.proxyoutpost.cache.enka.impl;

import me.darkkir3.proxyoutpost.cache.enka.AbstractEnkaFileCache;
import me.darkkir3.proxyoutpost.cache.enka.EnkaLocalizationCache;
import me.darkkir3.proxyoutpost.cache.enka.EnkaMedalCache;
import me.darkkir3.proxyoutpost.cache.enka.EnkaStoreType;
import me.darkkir3.proxyoutpost.configuration.EnkaAPIConfiguration;
import me.darkkir3.proxyoutpost.model.output.MedalOutput;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;
import tools.jackson.databind.JsonNode;
import tools.jackson.databind.ObjectMapper;

@Component
public class DefaultEnkaMedalCache extends AbstractEnkaFileCache implements EnkaMedalCache {

    private static final String MEDAL_CACHE = "ENKA_MEDAL_CACHE";
    private static final Logger log = LoggerFactory.getLogger(DefaultEnkaMedalCache.class);

    private final EnkaLocalizationCache enkaLocalizationCache;

    public DefaultEnkaMedalCache(EnkaAPIConfiguration enkaAPIConfiguration,
                                 CacheManager cacheManager,
                                 EnkaLocalizationCache enkaLocalizationCache) {
        super(enkaAPIConfiguration, cacheManager);
        this.enkaLocalizationCache = enkaLocalizationCache;
    }

    @Override
    public EnkaStoreType getStoreType() {
        return EnkaStoreType.MEDALS;
    }

    @Override
    public String getCacheName() {
        return MEDAL_CACHE;
    }

    /**
     * grab the medal by id while trying to translate fields like the name
     * @param id the id of the medal to fetch
     * @return the translated medal instance
     */
    @Cacheable(value = MEDAL_CACHE)
    @Override
    public MedalOutput getMedalById(String language, Long id) {
        JsonNode rootNode = this.getRootNode();
        if(rootNode != null) {
            JsonNode propertyNode = rootNode.get(String.valueOf(id));
            if(propertyNode != null) {
                ObjectMapper objectMapper = new ObjectMapper();
                MedalOutput medalOutput = objectMapper.treeToValue(propertyNode, MedalOutput.class);
                if(medalOutput != null) {
                    return this.transformMedalFields(medalOutput, language);
                }
                else {
                    log.error("Failed to parse {} for weapon id {}", this.getStoreName(), id);
                }
            }
            else {
                log.error("Could not find weapon with id {}", id);

            }
        }

        return null;
    }

    /**
     * modifies the passed medal pojo by translating fields and URLs
     * @param medalOutput the medal to transform
     * @return the transformed medal instance
     */
    private MedalOutput transformMedalFields(MedalOutput medalOutput, String language) {
        if(medalOutput != null) {
            if(!StringUtils.isBlank(medalOutput.getName())) {
                medalOutput.setName(this.enkaLocalizationCache.translate(language, medalOutput.getName()));
            }

            if(!StringUtils.isBlank(medalOutput.getIcon())) {
                medalOutput.setIcon(this.enkaAPIConfiguration.getBaseUrl() + medalOutput.getIcon());
            }

            if(!StringUtils.isBlank(medalOutput.getPrefixIcon())) {
                medalOutput.setPrefixIcon(this.enkaAPIConfiguration.getBaseUrl() + medalOutput.getPrefixIcon());
            }
        }

        return medalOutput;
    }
}
