package me.darkkir3.proxyoutpost.model.hakushin;

import me.darkkir3.proxyoutpost.configuration.HakushinAPIConfiguration;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import tools.jackson.core.JacksonException;
import tools.jackson.core.JsonParser;
import tools.jackson.databind.DeserializationContext;
import tools.jackson.databind.JsonNode;
import tools.jackson.databind.deser.std.StdDeserializer;

@Component
public class HakushinAgentDeserializer extends StdDeserializer<HakushinAgent> {

    private static HakushinAPIConfiguration hakushinAPIConfiguration;

    public HakushinAgentDeserializer(Class<?> vc) {
        super(vc);
    }

    /**
     * set the hakushin api configuration via spring since jackson does not apply dependency injection
     * @param hakushinAPIConfiguration
     */
    @Autowired
    public HakushinAgentDeserializer(HakushinAPIConfiguration hakushinAPIConfiguration) {
        this((Class<?>) null);
        HakushinAgentDeserializer.hakushinAPIConfiguration = hakushinAPIConfiguration;
    }

    @Override
    public HakushinAgent deserialize(JsonParser p, DeserializationContext ctxt) throws JacksonException {
        JsonNode root = p.readValueAs(JsonNode.class);

        HakushinAgent result = new HakushinAgent();
        this.readAgentCamp(result, root);
        this.readCoreSkillBonus(result, root);
        this.readCoreSkillDescriptions(result, root);
        this.readSkillDescriptions(result, root);

        return result;
    }

    private void readAgentCamp(HakushinAgent agent, JsonNode root) {
        JsonNode campNode = root.get(hakushinAPIConfiguration.getCharacterCamp());
        if(campNode != null) {
            campNode.forEachEntry((k, v) -> {
                //we only expect one key-value pair here
                String factionName = v.asString();
                if(!StringUtils.isBlank(factionName)) {
                    agent.setFactionName(factionName);
                    agent.setFactionIcon(
                            hakushinAPIConfiguration.getCharacterCampIconPrefix() +
                            factionName.trim() +
                            hakushinAPIConfiguration.getCharacterCampIconSuffix());
                }
            });
        }
    }

    private void readCoreSkillBonus(HakushinAgent agent, JsonNode root) {
        //TODO: implement me
    }

    private void readCoreSkillDescriptions(HakushinAgent agent, JsonNode root) {
        //TODO: implement me
    }

    private void readSkillDescriptions(HakushinAgent agent, JsonNode root) {
        //TODO: implement me
    }
}
