package me.darkkir3.proxyoutpost.controller;

import me.darkkir3.proxyoutpost.cache.EnkaProfileCache;
import me.darkkir3.proxyoutpost.model.db.Profile;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MainController {

    private final EnkaProfileCache enkaProfileCache;

    public MainController(EnkaProfileCache enkaProfileCache) {
        this.enkaProfileCache = enkaProfileCache;
    }

    @GetMapping("/profile/{id}")
    public Profile printProfile(@PathVariable("id") Long userId) {
        //https://enka.network/api/zzz/uid/1501331084
        return enkaProfileCache.getProfileByUid(userId);
    }
}
