package me.darkkir3.proxyoutpost.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("classpath:hakushin.properties")
public class HakushinAPIConfiguration {

    @Value("${hakushin.base-path}")
    private String basePath;
    @Value("${hakushin.character}")
    private String characterPath;
    @Value("${hakushin.character.camp}")
    private String characterCamp;
    @Value("${hakushin.character.camp.icon-prefix}")
    private String characterCampIconPrefix;
    @Value("${hakushin.character.camp.icon-suffix}")
    private String characterCampIconSuffix;

    /**
     * @return the base url for fetching data in hakushin api
     */
    public String getBasePath() {
        return basePath;
    }

    /**
     * @return the endpoint for fetching character data in hakushin
     */
    public String getCharacterPath() {
        return characterPath;
    }

    /**
     * @return the key for fetching the camp in hakushin character responses
     */
    public String getCharacterCamp() {
        return characterCamp;
    }

    /**
     * @return the prefix of the camp icon url in hakushin character responses
     */
    public String getCharacterCampIconPrefix() {
        return characterCampIconPrefix;
    }

    /**
     * @return the suffix of the camp icon url in hakushin character responses
     */
    public String getCharacterCampIconSuffix() {
        return characterCampIconSuffix;
    }
}
