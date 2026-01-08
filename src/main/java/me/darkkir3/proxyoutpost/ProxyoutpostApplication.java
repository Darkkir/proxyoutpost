package me.darkkir3.proxyoutpost;

import me.darkkir3.proxyoutpost.configuration.HakushinAPIConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cache.annotation.EnableCaching;

@EnableCaching
@EnableConfigurationProperties(HakushinAPIConfiguration.class)
@SpringBootApplication
public class ProxyoutpostApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProxyoutpostApplication.class, args);
	}

}
