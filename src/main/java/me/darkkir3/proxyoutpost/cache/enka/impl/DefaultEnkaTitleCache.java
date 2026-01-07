package me.darkkir3.proxyoutpost.cache.enka.impl;

import me.darkkir3.proxyoutpost.cache.enka.AbstractEnkaFileCache;
import me.darkkir3.proxyoutpost.cache.enka.EnkaLocalizationCache;
import me.darkkir3.proxyoutpost.cache.enka.EnkaStoreType;
import me.darkkir3.proxyoutpost.cache.enka.EnkaTitleCache;
import me.darkkir3.proxyoutpost.configuration.EnkaAPIConfiguration;
import me.darkkir3.proxyoutpost.model.output.TitleOutput;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;
import tools.jackson.databind.JsonNode;
import tools.jackson.databind.ObjectMapper;

@Component
public class DefaultEnkaTitleCache extends AbstractEnkaFileCache implements EnkaTitleCache {

    private static final String TITLE_CACHE = "ENKA_TITLE_CACHE";
    private static final Logger log = LoggerFactory.getLogger(DefaultEnkaTitleCache.class);

    private final EnkaLocalizationCache enkaLocalizationCache;

    public DefaultEnkaTitleCache(EnkaAPIConfiguration enkaAPIConfiguration,
                                 CacheManager cacheManager,
                                 EnkaLocalizationCache enkaLocalizationCache) {
        super(enkaAPIConfiguration, cacheManager);
        this.enkaLocalizationCache = enkaLocalizationCache;
    }

    @Override
    public EnkaStoreType getStoreType() {
        return EnkaStoreType.TITLES;
    }

    @Override
    public String getCacheName() {
        return TITLE_CACHE;
    }

    /**
     * grab the title by id while trying to translate fields like the name
     * @param id the id of the title to fetch
     * @return the translated title instance
     */
    @Cacheable(value = TITLE_CACHE)
    @Override
    public TitleOutput getTitleById(String language, Long id) {
        JsonNode rootNode = this.getRootNode();
        if(rootNode != null) {
            JsonNode propertyNode = rootNode.get(String.valueOf(id));
            if(propertyNode != null) {
                ObjectMapper objectMapper = new ObjectMapper();
                TitleOutput titleOutput = objectMapper.treeToValue(propertyNode, TitleOutput.class);
                if(titleOutput != null) {
                    return this.transformTitleFields(titleOutput, language);
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
     * modifies the passed title pojo by translating fields
     * and setting ids
     * @param titleOutput the title to transform
     * @return the transformed title instance
     */
    private TitleOutput transformTitleFields(TitleOutput titleOutput, String language) {
        if(titleOutput != null) {
            if(!StringUtils.isBlank(titleOutput.titleText)) {
                titleOutput.titleText = this.enkaLocalizationCache.translate(language, titleOutput.titleText);
                if(titleOutput.variants != null) {
                    titleOutput.variants.replaceAll((k, v) -> this.enkaLocalizationCache.translate(language, v));
                }
            }
        }

        return titleOutput;
    }
}
