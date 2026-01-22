package me.darkkir3.proxyoutpost.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @param basePath      the base url for fetching data in hakushin api
 * @param characterPath the endpoint for fetching character data in hakushin
 * @param character     character endpoint related data
 */
@ConfigurationProperties(prefix = "hakushin")
public record HakushinAPIProperties(
        String basePath,
        String characterPath,
        Character character) {

    /**
     * @param campKey        the key for fetching the camp in hakushin character responses
     * @param campIconPrefix the prefix of the camp icon url in hakushin character responses
     * @param campIconSuffix the suffix of the camp icon url in hakushin character responses
     * @param coreBonus      core-bonus related data
     * @param passive        passive (core + additional) related data
     * @param skill          skill related data (name and description)
     */
    public record Character(
            String campKey,
            String campIconPrefix,
            String campIconSuffix,
            PartnerInfo partnerInfo,
            CoreBonus coreBonus,
            Passive passive,
            Skill skill,
            Talent talent) {

        /**
         * @param key the key of the partner info property in the hakushin character response
         * @param name the key of the full name of the character in the PartnerInfo node
         */
        public record PartnerInfo(
                String key,
                String name
        ) {}

        /**
         * describes the keys needed to read the bonus stats of a core skill
         *
         * @param key            the key of the core bonus property in the hakushin character response
         * @param property       the key to the actual stat property of the core bonus in the hakushin character
         *                       response
         * @param maxLevel       the maximum core skill bonus level in the hakushin character response
         * @param propertyId     the key of the property id in the property stat node
         * @param propertyName   the key of the property name in the property stat node
         * @param propertyFormat the key of the property format in the property stat node
         * @param propertyValue  the key of the property value in the property stat node
         */
        public record CoreBonus(
                String key,
                String property,
                int maxLevel,
                String propertyId,
                String propertyName,
                String propertyFormat,
                String propertyValue) {
        }

        /**
         * describes the keys needed to read the description of a core skill at a specific level
         *
         * @param key                 the key of the passive property in the hakushin character response
         * @param property            the key to the node below the passive property
         * @param propertyLevel       the key of the level for each core skill entry
         * @param propertyName        the key of the name for each core skill entry
         * @param propertyDescription the key of the description for each core skill entry
         */
        public record Passive(
                String key,
                String property,
                String propertyLevel,
                String propertyName,
                String propertyDescription) {
        }

        /**
         * describes the keys needed to read the skill descriptions in the hakushin character response
         *
         * @param key                 the key of the skill property in the hakushin character response
         * @param basicAttack         the key of the basic attack skill
         * @param dash                the key of the dash/dodge skill
         * @param specialAttack       the key of the (ex-)special skill
         * @param ultimate            the key of the chain attack/ultimate skill
         * @param assist              the key for the assist follow-up skill
         * @param property            the key of the actual skill node
         * @param propertyName        the key of the skill name
         * @param propertyDescription the key of the skill description
         * @param propertyParam       the key of the skill parameter map
         */
        public record Skill(
                String key,
                String basicAttack,
                String dash,
                String specialAttack,
                String ultimate,
                String assist,
                String property,
                String propertyName,
                String propertyDescription,
                String propertyParam) {
        }

        /**
         * describes the keys needed to read the mindscape details in the hakushin character response
         * @param key the key of the talent property in the hakushin character response
         * @param propertyLevel the key of the mindscape level
         * @param propertyName the key of the mindscape name
         * @param propertyDescription the key of the mindscape description
         */
        public record Talent(
                String key,
                String propertyLevel,
                String propertyName,
                String propertyDescription) {
        }
    }
}