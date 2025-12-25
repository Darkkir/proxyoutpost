package me.darkkir3.proxyoutpost.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.transaction.Transactional;
import me.darkkir3.proxyoutpost.cache.*;
import me.darkkir3.proxyoutpost.configuration.EnkaAPIConfiguration;
import me.darkkir3.proxyoutpost.model.db.PlayerAgent;
import me.darkkir3.proxyoutpost.model.db.PlayerProfile;
import me.darkkir3.proxyoutpost.model.output.AgentOutput;
import me.darkkir3.proxyoutpost.model.output.WeaponOutput;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.List;

@RestController
public class MainController {

    private final EnkaProfileCache enkaProfileCache;
    private final EnkaLocalizationCache enkaLocalizationCache;
    private final EnkaAgentCache enkaAgentCache;
    private final EnkaWeaponCache enkaWeaponCache;
    private final EnkaPropertyCache enkaPropertyCache;
    private final EnkaAPIConfiguration enkaAPIConfiguration;

    public MainController(EnkaProfileCache enkaProfileCache,
                          EnkaLocalizationCache enkaLocalizationCache,
                          EnkaAgentCache enkaAgentCache,
                          EnkaWeaponCache enkaWeaponCache,
                          EnkaPropertyCache enkaPropertyCache,
                          EnkaAPIConfiguration enkaAPIConfiguration) {
        this.enkaProfileCache = enkaProfileCache;
        this.enkaLocalizationCache = enkaLocalizationCache;
        this.enkaAgentCache = enkaAgentCache;
        this.enkaWeaponCache = enkaWeaponCache;
        this.enkaPropertyCache = enkaPropertyCache;
        this.enkaAPIConfiguration = enkaAPIConfiguration;
    }

    @Tag(name = "showProfile", description = "Display all profile data based on the user id")
    @GetMapping("/profile/{id}")
    @Transactional
    public List<PlayerAgent> showProfile(@PathVariable("id") Long userId) {
        String languageToUse = enkaLocalizationCache.getDefaultLanguage();
        //https://enka.network/api/zzz/uid/1501331084
        PlayerProfile playerProfile = enkaProfileCache.getProfileByUid(languageToUse, userId);
        if(playerProfile != null) {
            List<PlayerAgent> agentsOfProfile = playerProfile.getAgentsList();
            if(agentsOfProfile != null) {
                agentsOfProfile.forEach(t -> {
                            AgentOutput agentOutput = enkaAgentCache.getAgentById(languageToUse, t.getAgentPk().getAgentId());
                            if(t.getWeapon() != null) {
                                WeaponOutput weaponOutput = enkaWeaponCache.getWeaponById(languageToUse, t.getWeapon().getWeaponId());
                                enkaWeaponCache.updatePlayerWeaponStats(t.getWeapon(), weaponOutput);
                            }
                            t.setAgentOutput(agentOutput);
                            enkaAgentCache.updatePlayerAgentStats(t, agentOutput);
                        });
                return agentsOfProfile;
            }
        }

        return Collections.emptyList();
    }
}
