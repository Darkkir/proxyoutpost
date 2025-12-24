package me.darkkir3.proxyoutpost.controller;

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

    @GetMapping("/profile/{id}")
    public List<PlayerAgent> printProfile(@PathVariable("id") Long userId) {
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
                                if(weaponOutput.getMainStat() != null) {
                                    t.getWeapon().setMainStatProperty(
                                            enkaPropertyCache.getPropertyById(languageToUse, weaponOutput.getMainStat().propertyId));
                                }
                                if(weaponOutput.getSecondaryStat() != null) {
                                    t.getWeapon().setSecondaryStatProperty(
                                            enkaPropertyCache.getPropertyById(languageToUse, weaponOutput.getSecondaryStat().propertyId));
                                }
                            }
                            t.setAgentOutput(agentOutput);
                        });
                return agentsOfProfile;
            }
        }

        return Collections.emptyList();
    }
}
