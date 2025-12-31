package me.darkkir3.proxyoutpost.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Schema;
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

    @Tag(name = "Profile", description = "Profile data")
    @Operation(summary = "Search for a profile via uid and language",
            description = "A profile including full agent data")
    @GetMapping("/{language}/profile/{id}")
    @Transactional
    public PlayerProfile showProfile(
            @Parameter(
                    in = ParameterIn.PATH,
                    description = "Language to translate all fields with",
                    required = true,
                    schema = @Schema(type="string"))
            @PathVariable("language") String languageToUse,
            @Parameter(
                    in = ParameterIn.PATH,
                    description = "The uid of the profile to search for",
                    required = true,
                    schema = @Schema(type="number"))
            @PathVariable("id") Long userId) {
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
                            enkaAgentCache.updatePlayerAgentStats(languageToUse, t, agentOutput);
                        });
            }
        }

        return playerProfile;
    }

    @Tag(name = "Profile", description = "Profile data")
    @Operation(summary = "Search for only the agents of a profile via uid and language",
            description = "List of agents for that profile")
    @GetMapping("/{language}/agents/{id}")
    @Transactional
    public List<PlayerAgent> showAgents(
            @Parameter(
                    in = ParameterIn.PATH,
                    description = "Language to translate all fields with",
                    required = true,
                    schema = @Schema(type="string"))
            @PathVariable("language") String languageToUse,
            @Parameter(
                    in = ParameterIn.PATH,
                    description = "The uid of the profile to search for",
                    required = true,
                    schema = @Schema(type="number"))
            @PathVariable("id") Long userId) {
        PlayerProfile playerProfile = this.showProfile(languageToUse, userId);
        return playerProfile.getAgentsList();
    }
}
