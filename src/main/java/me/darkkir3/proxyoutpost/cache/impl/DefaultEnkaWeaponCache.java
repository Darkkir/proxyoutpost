package me.darkkir3.proxyoutpost.cache.impl;

import me.darkkir3.proxyoutpost.cache.AbstractEnkaFileCache;
import me.darkkir3.proxyoutpost.cache.EnkaLocalizationCache;
import me.darkkir3.proxyoutpost.cache.EnkaStoreType;
import me.darkkir3.proxyoutpost.cache.EnkaWeaponCache;
import me.darkkir3.proxyoutpost.configuration.EnkaAPIConfiguration;
import me.darkkir3.proxyoutpost.model.output.WeaponOutput;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;
import tools.jackson.databind.JsonNode;
import tools.jackson.databind.ObjectMapper;

@Component
public class DefaultEnkaWeaponCache extends AbstractEnkaFileCache implements EnkaWeaponCache {

    private static final String WEAPON_CACHE = "WEAPON_CACHE";
    private static final Logger log = LoggerFactory.getLogger(DefaultEnkaWeaponCache.class);

    private final EnkaLocalizationCache enkaLocalizationCache;

    public DefaultEnkaWeaponCache(EnkaAPIConfiguration enkaAPIConfiguration, CacheManager cacheManager, EnkaLocalizationCache enkaLocalizationCache) {
        super(enkaAPIConfiguration, cacheManager);
        this.enkaLocalizationCache = enkaLocalizationCache;
    }

    @Override
    public EnkaStoreType getStoreType() {
        return EnkaStoreType.WEAPONS;
    }

    @Override
    public String getCacheName() {
        return WEAPON_CACHE;
    }

    /**
     * grab the weapon by id while trying to translate fields like the name
     * @param id the id of the weapon to fetch
     * @return the translated weapon instance
     */
    @Cacheable(value= WEAPON_CACHE)
    @Override
    public WeaponOutput getWeaponById(String language, Long id) {
        JsonNode rootNode = this.getRootNode();
        if(rootNode != null) {
            JsonNode weaponNode = rootNode.get(String.valueOf(id));
            if(weaponNode != null) {
                ObjectMapper objectMapper = new ObjectMapper();
                WeaponOutput weaponOutput = objectMapper.treeToValue(weaponNode, WeaponOutput.class);
                if(weaponOutput != null) {
                    return this.transformWeaponFields(weaponOutput, id, language);
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
     * modifies the passed weapon pojo by translating fields
     * and setting ids
     * @param weaponOutput the weapon to transform
     * @return the transformed agent instance
     */
    private WeaponOutput transformWeaponFields(WeaponOutput weaponOutput, Long id, String language) {
        if(weaponOutput != null) {

        }
        return weaponOutput;
    }
}
