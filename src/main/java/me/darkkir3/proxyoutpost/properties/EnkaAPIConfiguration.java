package me.darkkir3.proxyoutpost.properties;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("classpath:enka-api.properties")
public class EnkaAPIConfiguration {
    @Value("${enka.user-agent}")
    public String userAgent;
}
