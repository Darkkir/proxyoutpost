package me.darkkir3.proxyoutpost.cache.impl;

import me.darkkir3.proxyoutpost.cache.AbstractEnkaFileCache;
import me.darkkir3.proxyoutpost.cache.EnkaLocalizationCache;
import me.darkkir3.proxyoutpost.cache.EnkaPropertyCache;
import me.darkkir3.proxyoutpost.cache.EnkaStoreType;
import me.darkkir3.proxyoutpost.configuration.EnkaAPIConfiguration;
import me.darkkir3.proxyoutpost.model.output.PropertyOutput;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;
import tools.jackson.databind.JsonNode;
import tools.jackson.databind.ObjectMapper;

@Component
public class DefaultEnkaPropertyCache extends AbstractEnkaFileCache implements EnkaPropertyCache {

    private static final String PROPERTY_CACHE = "PROPERTY_CACHE";
    private static final Logger log = LoggerFactory.getLogger(DefaultEnkaPropertyCache.class);

    private final EnkaLocalizationCache enkaLocalizationCache;

    public DefaultEnkaPropertyCache(EnkaAPIConfiguration enkaAPIConfiguration, CacheManager cacheManager, EnkaLocalizationCache enkaLocalizationCache) {
        super(enkaAPIConfiguration, cacheManager);
        this.enkaLocalizationCache = enkaLocalizationCache;
    }

    @Override
    public EnkaStoreType getStoreType() {
        return EnkaStoreType.PROPERTY;
    }

    @Override
    public String getCacheName() {
        return PROPERTY_CACHE;
    }

    /**
     * grab the property by id while trying to translate fields like the name
     * @param id the id of the weapon to fetch
     * @return the translated weapon instance
     */
    @Cacheable(value= PROPERTY_CACHE)
    @Override
    public PropertyOutput getPropertyById(String language, Long id) {
        JsonNode rootNode = this.getRootNode();
        if(rootNode != null) {
            JsonNode propertyNode = rootNode.get(String.valueOf(id));
            if(propertyNode != null) {
                ObjectMapper objectMapper = new ObjectMapper();
                PropertyOutput propertyOutput = objectMapper.treeToValue(propertyNode, PropertyOutput.class);
                if(propertyOutput != null) {
                    return this.transformPropertyFields(propertyOutput, language);
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
     * modifies the passed property pojo by translating fields
     * and setting ids
     * @param propertyOutput the property to transform
     * @return the transformed agent instance
     */
    private PropertyOutput transformPropertyFields(PropertyOutput propertyOutput, String language) {
        if(propertyOutput != null) {
            if(!StringUtils.isBlank(propertyOutput.getInternalName())) {
                //TODO: translate the name of this property
                propertyOutput.setTranslatedName(this.enkaLocalizationCache.translate(language, propertyOutput.getInternalName()));
            }
        }
        return propertyOutput;
    }
}
