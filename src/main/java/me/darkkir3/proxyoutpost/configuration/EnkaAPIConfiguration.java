package me.darkkir3.proxyoutpost.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import java.util.Map;

@Configuration
@PropertySource("classpath:enka-api.properties")
public class EnkaAPIConfiguration {

    @Value("${enka.user-agent}")
    private String userAgent;
    @Value("${enka.api-url}")
    private String apiUrl;
    @Value("${enka.base-url}")
    private String baseUrl;
    @Value("${enka.uid-endpoint}")
    private String uidEndpoint;
    @Value("${enka.min-ttl}")
    private int minTtlInSeconds;
    @Value("${enka.cache-time}")
    private int cacheTimeInSeconds;
    @Value("${enka.config.refresh-time}")
    private int refreshTimeInHours;
    @Value("${enka.config.path}")
    private String configurationPath;
    @Value("#{${enka.config.stores}}")
    private Map<String, String> storesMap;

    /**
     * @return the user-agent to use when making api calls to enka
     */
    public String getUserAgent() {
        return userAgent;
    }

    /**
     * @return the base url for api endcalls
     */
    public String getApiUrl() {
        return apiUrl;
    }

    /**
     * @return the base url of enka
     */
    public String getBaseUrl() {
        return baseUrl;
    }

    /**
     * @return the specific endpoint to fetch profile data
     */
    public String getUidEndpoint() {
        return uidEndpoint;
    }

    /**
     * @return how much time in seconds we add to each profiles ttl manually
     */
    public int getMinTtlInSeconds() {
        return minTtlInSeconds;
    }

    /**
     * @return how often we refresh json configs from github
     */
    public int getRefreshTimeInHours() {
        return refreshTimeInHours;
    }

    /**
     * @return base path for all downloaded configuration files
     */
    public String getConfigurationPath() {
        return configurationPath;
    }

    /**
     * @return profile cache time in seconds (how often we check for expired profiles)
     */
    public int getCacheTimeInSeconds() {
        return cacheTimeInSeconds;
    }

    /**
     * @return a map of all enka-api store files including their url
     */
    public Map<String, String> getStoresMap() {
        return storesMap;
    }
}
