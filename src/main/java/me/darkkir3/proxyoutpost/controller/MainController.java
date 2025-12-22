package me.darkkir3.proxyoutpost.controller;

import me.darkkir3.proxyoutpost.cache.EnkaAgentCache;
import me.darkkir3.proxyoutpost.cache.EnkaLocalizationCache;
import me.darkkir3.proxyoutpost.cache.EnkaProfileCache;
import me.darkkir3.proxyoutpost.configuration.EnkaAPIConfiguration;
import me.darkkir3.proxyoutpost.model.db.Agent;
import me.darkkir3.proxyoutpost.model.db.Profile;
import me.darkkir3.proxyoutpost.model.output.AgentOutput;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@RestController
public class MainController {

    private final EnkaProfileCache enkaProfileCache;
    private final EnkaLocalizationCache enkaLocalizationCache;
    private final EnkaAgentCache enkaAgentCache;
    private final EnkaAPIConfiguration enkaAPIConfiguration;

    public MainController(EnkaProfileCache enkaProfileCache, EnkaLocalizationCache enkaLocalizationCache, EnkaAgentCache enkaAgentCache, EnkaAPIConfiguration enkaAPIConfiguration) {
        this.enkaProfileCache = enkaProfileCache;
        this.enkaLocalizationCache = enkaLocalizationCache;
        this.enkaAgentCache = enkaAgentCache;
        this.enkaAPIConfiguration = enkaAPIConfiguration;
    }

    @GetMapping("/profile/{id}")
    public List<AgentOutput> printProfile(@PathVariable("id") Long userId) {
        //https://enka.network/api/zzz/uid/1501331084
        Profile profile = enkaProfileCache.getProfileByUid(userId);
        if(profile != null) {
            List<Agent> agentsOfProfile = profile.getAgentsList();
            if(agentsOfProfile != null) {
                List<AgentOutput> outputList = new ArrayList<>();
                agentsOfProfile.forEach(t ->
                        outputList.add(enkaAgentCache.getAgentById(t.getAgentPk().getAgentId())));
                return outputList;
            }
        }

        return Collections.emptyList();
    }
}
