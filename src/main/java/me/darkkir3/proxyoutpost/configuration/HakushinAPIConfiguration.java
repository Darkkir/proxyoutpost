package me.darkkir3.proxyoutpost.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;

/**
 * @param basePath the base url for fetching data in hakushin api
 * @param characterPath the endpoint for fetching character data in hakushin
 * @param character character endpont related data
 */
@PropertySource("classpath:hakushin.properties")
@ConfigurationProperties(prefix = "hakushin")
public record HakushinAPIConfiguration (
        String basePath,
        String characterPath,
        Character character) {

    /**
     * @param campKey the key for fetching the camp in hakushin character responses
     * @param campIconPrefix the prefix of the camp icon url in hakushin character responses
     * @param campIconSuffix the suffix of the camp icon url in hakushin character responses
     * @param coreBonus core-bonus related data
     */
    public record Character (
            String campKey,
            String campIconPrefix,
            String campIconSuffix,
            CoreBonus coreBonus) {

        /**
         * @param key the key of the core bonus property in the hakushin character response
         * @param property the key to the actual stat property of the core bonus in the hakushin character response
         * @param maxLevel the maximum core skill bonus level in the hakushin character response
         * @param propertyId the key of the property id in the property stat node
         * @param propertyName the key of the property name in the property stat node
         * @param propertyFormat the key of the property format in the property stat node
         * @param propertyValue the key of the property value in the property stat node
         */
        public record CoreBonus(
                String key,
                String property,
                int maxLevel,
                String propertyId,
                String propertyName,
                String propertyFormat,

                String propertyValue) {}
    }
}