package me.darkkir3.proxyoutpost.controller;

import me.darkkir3.proxyoutpost.configuration.EnkaAPIConfiguration;
import me.darkkir3.proxyoutpost.model.json.ZZZProfile;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestClient;

@RestController
public class MainController {

    private EnkaAPIConfiguration enkaAPIConfiguration;
    private RestClient restClient;

    public MainController(EnkaAPIConfiguration enkaAPIConfiguration) {
        this.enkaAPIConfiguration = enkaAPIConfiguration;
        this.restClient = RestClient.create(this.enkaAPIConfiguration.getApiUrl());
    }

    @GetMapping("/profile/{id}")
    public String printProfile(@PathVariable("id") Long userId) {
        StringBuilder result = new StringBuilder();
        String lineSeparator = System.lineSeparator();
        //https://enka.network/api/zzz/uid/1501331084
        ZZZProfile profile = restClient.get().uri("uid/" + userId).retrieve().body(ZZZProfile.class);
        return result.toString();
    }
}
