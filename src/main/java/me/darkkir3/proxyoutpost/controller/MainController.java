package me.darkkir3.proxyoutpost.controller;

import jakarta.annotation.PostConstruct;
import me.darkkir3.proxyoutpost.cache.EnkaLocalizationCache;
import me.darkkir3.proxyoutpost.cache.EnkaProfileCache;
import me.darkkir3.proxyoutpost.configuration.EnkaAPIConfiguration;
import me.darkkir3.proxyoutpost.model.db.Profile;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MainController {

    private final EnkaProfileCache enkaProfileCache;
    private final EnkaLocalizationCache enkaLocalizationCache;
    private final EnkaAPIConfiguration enkaAPIConfiguration;

    public MainController(EnkaProfileCache enkaProfileCache, EnkaLocalizationCache enkaLocalizationCache, EnkaAPIConfiguration enkaAPIConfiguration) {
        this.enkaProfileCache = enkaProfileCache;
        this.enkaLocalizationCache = enkaLocalizationCache;
        this.enkaAPIConfiguration = enkaAPIConfiguration;
    }

    @PostConstruct
    public void setup() {
        System.out.println(this);
        System.out.println();
    }

    @GetMapping("/profile/{id}")
    public Profile printProfile(@PathVariable("id") Long userId) {
        //https://enka.network/api/zzz/uid/1501331084
        return enkaProfileCache.getProfileByUid(userId);
    }
}
