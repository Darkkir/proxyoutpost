package me.darkkir3.proxyoutpost.cache.hakushin.impl;

import jakarta.annotation.PostConstruct;
import me.darkkir3.proxyoutpost.cache.hakushin.HakushinHelper;
import me.darkkir3.proxyoutpost.configuration.EnkaAPIConfiguration;
import me.darkkir3.proxyoutpost.configuration.HakushinAPIConfiguration;
import me.darkkir3.proxyoutpost.model.hakushin.HakushinAgent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

@Component
public class DefaultHakushinHelper implements HakushinHelper {

    private static final Logger log = LoggerFactory.getLogger(DefaultHakushinHelper.class);
    private final EnkaAPIConfiguration enkaAPIConfiguration;
    private final HakushinAPIConfiguration hakushinAPIConfiguration;
    private RestClient restClient;

    public DefaultHakushinHelper(EnkaAPIConfiguration enkaAPIConfiguration,
                                 HakushinAPIConfiguration hakushinAPIConfiguration) {
        this.enkaAPIConfiguration = enkaAPIConfiguration;
        this.hakushinAPIConfiguration = hakushinAPIConfiguration;
    }

    @PostConstruct
    private void setUpRestClient() {
        this.restClient = RestClient.builder()
                .baseUrl(this.hakushinAPIConfiguration.getProperties().basePath())
                .defaultHeader("User-Agent", this.enkaAPIConfiguration.getUserAgent()).build();
    }

    public HakushinAgent fetchHakushinAgentById(String languageToUse, Long agentId) {
        if(agentId == null || agentId <= 0) {
            log.error("Tried to contact hakushin api with an invalid agent id");
            return null;
        }

        String endpointUrl = languageToUse +
                hakushinAPIConfiguration.getProperties().characterPath();

        return this.restClient.get()
                .uri(endpointUrl, agentId)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .body(HakushinAgent.class);
    }
}
