package me.darkkir3.proxyoutpost;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@EnableCaching
@SpringBootApplication
public class ProxyoutpostApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProxyoutpostApplication.class, args);
	}

}
