package me.darkkir3.proxyoutpost.configuration;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("classpath:hakushin.properties")
@EnableConfigurationProperties(HakushinAPIProperties.class)
public class HakushinAPIConfiguration {

    private final HakushinAPIProperties properties;

    public HakushinAPIConfiguration(HakushinAPIProperties properties) {
        this.properties = properties;
    }

    public HakushinAPIProperties getProperties() {
        return properties;
    }
}
