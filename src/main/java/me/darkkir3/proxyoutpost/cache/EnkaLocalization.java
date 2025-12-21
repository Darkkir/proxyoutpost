package me.darkkir3.proxyoutpost.cache;

public interface EnkaLocalization {

    /**
     * @return the default language to use
     */
    public String getDefaultLanguage();

    /**
     * translate the given key in the given language based on locs.JSON
     * @param language the language to use
     * @param key the key to find
     * @return the translation
     */
    public String translate(String language, String key);
}
