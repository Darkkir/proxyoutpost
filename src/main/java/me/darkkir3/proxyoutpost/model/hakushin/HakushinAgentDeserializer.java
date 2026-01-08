package me.darkkir3.proxyoutpost.model.hakushin;

import me.darkkir3.proxyoutpost.configuration.HakushinAPIConfiguration;
import me.darkkir3.proxyoutpost.model.output.ItemProperty;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import tools.jackson.core.JacksonException;
import tools.jackson.core.JsonParser;
import tools.jackson.databind.DeserializationContext;
import tools.jackson.databind.JsonNode;
import tools.jackson.databind.ValueDeserializer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Component
public class HakushinAgentDeserializer extends ValueDeserializer<HakushinAgent> {

    private static HakushinAPIConfiguration hakushinAPIConfiguration;

    public HakushinAgentDeserializer() {
        super();
    }

    /**
     * set the hakushin api configuration via spring since jackson does not apply dependency injection
     * @param hakushinAPIConfiguration the api configuration to set
     */
    @SuppressWarnings({"java:S3010"})
    @Autowired
    public HakushinAgentDeserializer(HakushinAPIConfiguration hakushinAPIConfiguration) {
        this();
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

        /**
         * "Camp": {
         *     "11": "Spook Shack"
         *   }
         */

        JsonNode campNode = root.get(hakushinAPIConfiguration.character().campKey());
        if(campNode != null) {
            campNode.forEachEntry((k, v) -> {
                //we only expect one key-value pair here
                String factionName = v.asString();
                if(!StringUtils.isBlank(factionName)) {
                    agent.setFactionName(factionName);
                    agent.setFactionIcon(
                            hakushinAPIConfiguration.character().campIconPrefix() +
                            factionName.trim() +
                            hakushinAPIConfiguration.character().campIconSuffix());
                }
            });
        }
    }

    private void readCoreSkillBonus(HakushinAgent agent, JsonNode root) {

        /**
         * "ExtraLevel": {
         *     "1": {
         *       "MaxLevel": 15,
         *       "Extra": {
         *         "31401": {
         *           "Prop": 31401,
         *           "Name": "Anomaly Mastery",
         *           "Format": "{0:0.#}",
         *           "Value": 12
         *         },
         *         "12101": {
         *           "Prop": 12101,
         *           "Name": "Base ATK",
         *           "Format": "{0:0.#}",
         *           "Value": 0
         *         }
         *       }
         *     },
         *     "2": {
         *       "MaxLevel": 25,
         *       "Extra": {
         *         "31401": {
         *           "Prop": 31401,
         *           "Name": "Anomaly Mastery",
         *           "Format": "{0:0.#}",
         *           "Value": 12
         *         },
         *         "12101": {
         *           "Prop": 12101,
         *           "Name": "Base ATK",
         *           "Format": "{0:0.#}",
         *           "Value": 25
         *         }
         *       }
         *     },...
         */

        JsonNode coreBonusRoot = root.get(hakushinAPIConfiguration.character().coreBonus().key());
        if(coreBonusRoot != null) {
            HashMap<Integer, List<ItemProperty>> propertyBonusMap = new HashMap<>();
            for(int i = 0; i < hakushinAPIConfiguration.character().coreBonus().maxLevel(); i++) {
                List<ItemProperty> bonusProperties = new ArrayList<>();
                JsonNode coreBonusNode = coreBonusRoot.get(String.valueOf(i));
                if(coreBonusNode != null) {
                    JsonNode propertyRoot = coreBonusNode.get(hakushinAPIConfiguration.character().coreBonus().property());
                    if(propertyRoot != null) {
                        propertyRoot.forEachEntry((k, v) -> {
                            long propertyId =
                                    v.get(hakushinAPIConfiguration.character().coreBonus().propertyId()).asLong(0L);
                            String propertyName =
                                    v.get(hakushinAPIConfiguration.character().coreBonus().propertyName()).asString(
                                            "Undefined");
                            String propertyFormat =
                                    v.get(hakushinAPIConfiguration.character().coreBonus().propertyFormat()).asString(
                                            "Undefined");
                            int propertyValue =
                                    v.get(hakushinAPIConfiguration.character().coreBonus().propertyFormat()).asInt(0);

                            bonusProperties.add(new ItemProperty(propertyId, propertyValue, propertyName, propertyFormat));
                        });
                    }
                }
                propertyBonusMap.put(i, bonusProperties);
            }
            agent.setCoreSkillBonus(propertyBonusMap);
        }
    }

    private void readCoreSkillDescriptions(HakushinAgent agent, JsonNode root) {
        //TODO: implement me
    }

    private void readSkillDescriptions(HakushinAgent agent, JsonNode root) {
        //TODO: implement me
    }
}
