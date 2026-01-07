package me.darkkir3.proxyoutpost.cache.enka.impl;

import me.darkkir3.proxyoutpost.cache.enka.AbstractEnkaFileCache;
import me.darkkir3.proxyoutpost.cache.enka.EnkaLocalizationCache;
import me.darkkir3.proxyoutpost.cache.enka.EnkaStoreType;
import me.darkkir3.proxyoutpost.configuration.EnkaAPIConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;
import tools.jackson.databind.JsonNode;

import java.util.Locale;

@Component
public class DefaultEnkaLocalizationCache extends AbstractEnkaFileCache implements EnkaLocalizationCache {

    private static final String LOCALIZATION_CACHE = "ENKA_LOCALIZATION_CACHE";
    private static final Logger log = LoggerFactory.getLogger(DefaultEnkaLocalizationCache.class);

    public DefaultEnkaLocalizationCache(EnkaAPIConfiguration enkaAPIConfiguration, CacheManager cacheManager) {
        super(enkaAPIConfiguration, cacheManager);
    }

    @Override
    public EnkaStoreType getStoreType() {
        return EnkaStoreType.LOCS;
    }

    @Override
    public String getCacheName() {
        return LOCALIZATION_CACHE;
    }

    /**
     * @return the default localization language to use
     */
    @Override
    public String getDefaultLanguage() {
        return Locale.getDefault().getLanguage();
    }

    @Cacheable(value = LOCALIZATION_CACHE)
    @Override
    public String translate(String language, String key) {
        JsonNode rootNode = this.getRootNode();
        if(rootNode != null) {
            JsonNode languageNode = rootNode.get(language);
            if(languageNode != null) {
                JsonNode translationNode = languageNode.get(key);
                if(translationNode != null) {
                    return translationNode.asString(key);
                }
                else {
                    log.error("Tried to look up {} in language {}, but no entry was found", key, language);
                }
            } else {
                log.error("Could not find language node for language {}", language);
            }
        }

        return null;
    }
}
