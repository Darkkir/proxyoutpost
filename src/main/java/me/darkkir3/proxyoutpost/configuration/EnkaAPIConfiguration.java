package me.darkkir3.proxyoutpost.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("classpath:enka-api.properties")
public class EnkaAPIConfiguration {
    @Value("${enka.user-agent}")
    private String userAgent;
    @Value("${enka.api-url}")
    private String apiUrl;
    @Value("${enka.uid-endpoint}")
    private String uidEndpoint;

    public String getUserAgent() {
        return userAgent;
    }

    public String getApiUrl() {
        return apiUrl;
    }

    public String getUidEndpoint() {
        return uidEndpoint;
    }
}
