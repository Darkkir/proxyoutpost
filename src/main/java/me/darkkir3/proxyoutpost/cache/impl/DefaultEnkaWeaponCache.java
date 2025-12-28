package me.darkkir3.proxyoutpost.cache.impl;

import me.darkkir3.proxyoutpost.cache.*;
import me.darkkir3.proxyoutpost.configuration.EnkaAPIConfiguration;
import me.darkkir3.proxyoutpost.model.db.PlayerWeapon;
import me.darkkir3.proxyoutpost.model.output.WeaponOutput;
import me.darkkir3.proxyoutpost.equipment.ItemPropertyTranslator;
import org.apache.commons.lang3.StringUtils;
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
    private final EnkaPropertyCache enkaPropertyCache;
    private final ItemPropertyTranslator itemPropertyTranslator;

    public DefaultEnkaWeaponCache(
            EnkaAPIConfiguration enkaAPIConfiguration,
            CacheManager cacheManager,
            EnkaLocalizationCache enkaLocalizationCache,
            EnkaPropertyCache enkaPropertyCache,
            ItemPropertyTranslator itemPropertyTranslator) {
        super(enkaAPIConfiguration, cacheManager);
        this.enkaLocalizationCache = enkaLocalizationCache;
        this.enkaPropertyCache = enkaPropertyCache;
        this.itemPropertyTranslator = itemPropertyTranslator;
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
    @Cacheable(value = WEAPON_CACHE)
    @Override
    public WeaponOutput getWeaponById(String language, Long id) {
        JsonNode rootNode = this.getRootNode();
        if(rootNode != null) {
            JsonNode weaponNode = rootNode.get(String.valueOf(id));
            if(weaponNode != null) {
                ObjectMapper objectMapper = new ObjectMapper();
                WeaponOutput weaponOutput = objectMapper.treeToValue(weaponNode, WeaponOutput.class);
                if(weaponOutput != null) {
                    this.transformWeaponFields(weaponOutput, language, id);
                    return weaponOutput;
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

    @Override
    public void updatePlayerWeaponStats(PlayerWeapon playerWeapon, WeaponOutput weaponOutput) {
        if(playerWeapon != null && weaponOutput != null) {
            playerWeapon.setWeaponOutput(weaponOutput);
            //only update when the value isn't already set
            if(!playerWeapon.isMainStatSet() || !playerWeapon.isSecondaryStatSet()) {
                itemPropertyTranslator.translateWeaponProperties(playerWeapon);
            }
        }
    }

    /**
     * modifies the passed weapon pojo by translating fields
     * and setting ids
     * @param weaponOutput the weapon to transform
     * @return the transformed weapon instance
     */
    private void transformWeaponFields(WeaponOutput weaponOutput, String language, Long id) {
        if(weaponOutput != null) {
            if(!StringUtils.isBlank(weaponOutput.itemName)) {
                weaponOutput.itemName = this.enkaLocalizationCache.translate(language, weaponOutput.itemName);
            }

            if(!StringUtils.isBlank(weaponOutput.imagePath)) {
                weaponOutput.imagePath = this.enkaAPIConfiguration.getBaseUrl() + weaponOutput.imagePath;
            }

            if(weaponOutput.getMainStat() != null) {
                weaponOutput.setMainStatPropertyOutput(
                        enkaPropertyCache.getPropertyById(language, weaponOutput.getMainStat().propertyId));
            }

            if(weaponOutput.getSecondaryStat() != null) {
                weaponOutput.setSecondaryStatPropertyOutput(
                        enkaPropertyCache.getPropertyById(language, weaponOutput.getSecondaryStat().propertyId));
            }
        }
    }
}
