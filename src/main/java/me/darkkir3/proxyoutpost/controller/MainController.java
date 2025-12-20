package me.darkkir3.proxyoutpost.controller;

import me.darkkir3.proxyoutpost.configuration.EnkaAPIConfiguration;
import me.darkkir3.proxyoutpost.model.db.Profile;
import me.darkkir3.proxyoutpost.model.enka.ZZZProfile;
import me.darkkir3.proxyoutpost.rep.ProfileRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestClient;

@RestController
public class MainController {

    private EnkaAPIConfiguration enkaAPIConfiguration;
    private RestClient restClient;
    private ProfileRepository profileRepository;

    public MainController(EnkaAPIConfiguration enkaAPIConfiguration, ProfileRepository profileRepository) {
        this.enkaAPIConfiguration = enkaAPIConfiguration;
        this.profileRepository = profileRepository;
        this.restClient = RestClient.create(this.enkaAPIConfiguration.getApiUrl());
    }

    @GetMapping("/profile/{id}")
    public String printProfile(@PathVariable("id") Long userId) {
        StringBuilder result = new StringBuilder();
        String lineSeparator = System.lineSeparator();
        //https://enka.network/api/zzz/uid/1501331084
        ZZZProfile jsonProfile = restClient.get().uri("uid/" + userId).retrieve().body(ZZZProfile.class);

        Profile dbProfile = new Profile();
        dbProfile.mapEnkaDataToDB(jsonProfile);
        result.append(dbProfile.toString());

        profileRepository.saveAndFlush(dbProfile);

        return result.toString();
    }
}
