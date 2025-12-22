package me.darkkir3.proxyoutpost.cache;

/**
 * Caches enka localization data (translations)
 */
public interface EnkaLocalizationCache {

    /**
     * @return the default language to use
     */
    public String getDefaultLanguage();

    /**
     * translate the given key in the default language based on locs.JSON
     * @param key the key to find
     * @return the translation
     */
    public default String translate(String key) {
        return this.translate(this.getDefaultLanguage(), key);
    }

    /**
     * translate the given key in the given language based on locs.JSON
     * @param language the language to use
     * @param key the key to find
     * @return the translation
     */
    public String translate(String language, String key);
}
