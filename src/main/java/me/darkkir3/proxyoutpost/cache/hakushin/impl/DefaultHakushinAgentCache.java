package me.darkkir3.proxyoutpost.cache.hakushin.impl;

import jakarta.annotation.PostConstruct;
import me.darkkir3.proxyoutpost.cache.hakushin.HakushinAgentCache;
import me.darkkir3.proxyoutpost.configuration.EnkaAPIConfiguration;
import me.darkkir3.proxyoutpost.configuration.HakushinAPIConfiguration;
import me.darkkir3.proxyoutpost.model.hakushin.HakushinAgent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

@Component
public class DefaultHakushinAgentCache implements HakushinAgentCache {

    private static final Logger log = LoggerFactory.getLogger(DefaultHakushinAgentCache.class);
    private final EnkaAPIConfiguration enkaAPIConfiguration;
    private final HakushinAPIConfiguration hakushinAPIConfiguration;
    private RestClient restClient;


    public DefaultHakushinAgentCache(EnkaAPIConfiguration enkaAPIConfiguration, HakushinAPIConfiguration hakushinAPIConfiguration) {
        this.enkaAPIConfiguration = enkaAPIConfiguration;
        this.hakushinAPIConfiguration = hakushinAPIConfiguration;
    }

    @PostConstruct
    private void setUpRestClient() {
        this.restClient = RestClient.builder()
                .baseUrl(this.hakushinAPIConfiguration.getBasePath())
                .defaultHeader("User-Agent", this.enkaAPIConfiguration.getUserAgent()).build();
    }

    public HakushinAgent fetchHakushinAgentById(String languageToUse, Long agentId) {
        if(agentId == null || agentId <= 0) {
            log.error("Tried to contact hakushin api with an invalid agent id");
            return null;
        }

        String endpointUrl = languageToUse +
                hakushinAPIConfiguration.getCharacterPath();

        HakushinAgent result = this.restClient.get()
                .uri(endpointUrl, agentId)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .body(HakushinAgent.class);

        //TODO: cache the result

        return result;
    }
}
