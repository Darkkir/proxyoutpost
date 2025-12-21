package me.darkkir3.proxyoutpost.controller;

import me.darkkir3.proxyoutpost.model.db.Profile;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MainController {

    private EnkaCache enkaCache;

    public MainController(EnkaCache enkaCache) {
        this.enkaCache = enkaCache;
    }

    @GetMapping("/profile/{id}")
    public Profile printProfile(@PathVariable("id") Long userId) {
        //https://enka.network/api/zzz/uid/1501331084
        return enkaCache.getProfileByUid(userId);
    }
}
