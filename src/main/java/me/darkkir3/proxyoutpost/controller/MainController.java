package me.darkkir3.proxyoutpost.controller;

import me.darkkir3.proxyoutpost.properties.EnkaAPIConfiguration;
import me.kazury.enkanetworkapi.enka.EnkaNetworkAPI;
import me.kazury.enkanetworkapi.enka.EnkaNetworkBuilder;
import me.kazury.enkanetworkapi.util.GlobalLocalization;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MainController {

    private EnkaAPIConfiguration enkaAPIConfiguration;
    private EnkaNetworkAPI networkAPI;

    public MainController(EnkaAPIConfiguration enkaAPIConfiguration) {
        this.enkaAPIConfiguration = enkaAPIConfiguration;
        networkAPI = new EnkaNetworkBuilder()
                .setDefaultLocalization(GlobalLocalization.GERMAN)
                .setUserAgent(enkaAPIConfiguration.userAgent)
                .setZenlessEnabled(true).build();

    }

    @GetMapping("/profile/{id}")
    public String printProfile(@PathVariable("id") Long userId) {
        StringBuilder result = new StringBuilder();
        String lineSeparator = System.lineSeparator();
        networkAPI.fetchZenlessUser(userId, (user) -> {

        });
        return result.toString();
    }
}
