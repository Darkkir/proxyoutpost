package me.darkkir3.proxyoutpost.model.hakushin;

import me.darkkir3.proxyoutpost.configuration.HakushinAPIConfiguration;
import me.darkkir3.proxyoutpost.model.db.SkillType;
import me.darkkir3.proxyoutpost.model.output.ItemProperty;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import tools.jackson.core.JacksonException;
import tools.jackson.core.JsonParser;
import tools.jackson.databind.DeserializationContext;
import tools.jackson.databind.JsonNode;
import tools.jackson.databind.ValueDeserializer;

import java.util.*;

@Component
public class HakushinAgentDeserializer extends ValueDeserializer<HakushinAgent> {

    private static final String UNDEFINED_VALUE = "Undefined";

    private static HakushinAPIConfiguration hakushinAPIConfiguration;

    public HakushinAgentDeserializer() {
        super();
    }

    /**
     * set the hakushin api configuration via spring since jackson does not apply dependency injection
     *
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
        this.readPartnerInfo(result, root);
        this.readAgentCamp(result, root);
        this.readCoreSkillBonus(result, root);
        this.readCoreSkillDescriptions(result, root);
        this.readSkillDescriptions(result, root);
        this.readTalentDescriptions(result, root);

        return result;
    }

    @SuppressWarnings("java:S125")
    private void readPartnerInfo(HakushinAgent agent, JsonNode root) {

        /*
         * "PartnerInfo": {
         *     "Birthday": "JAN 5",
         *     "FullName": "Komano Manato",
         */

        JsonNode partnerNode = root.get(hakushinAPIConfiguration.getProperties().character().partnerInfo().key());
        if (partnerNode != null) {
            JsonNode name = partnerNode.get(hakushinAPIConfiguration.getProperties().character().partnerInfo().name());
            if(name != null) {
                agent.setFullName(name.asString(UNDEFINED_VALUE));
            }
        }

    }

    @SuppressWarnings("java:S125")
    private void readAgentCamp(HakushinAgent agent, JsonNode root) {

        /*
         * "Camp": {
         *     "11": "Spook Shack"
         *   }
         */

        JsonNode campNode = root.get(hakushinAPIConfiguration.getProperties().character().campKey());
        if (campNode != null) {
            campNode.forEachEntry((k, v) -> {
                //we only expect one key-value pair here
                String factionName = v.asString();
                if (!StringUtils.isBlank(factionName)) {
                    agent.setFactionName(factionName);
                    agent.setFactionIcon(StringUtils.deleteWhitespace(factionName).replace("\\.", ""));

                    //map it to the real url, sometimes the name and the icon url differs
                    String actualUrl =
                            hakushinAPIConfiguration.getProperties().campIconMap().get(agent.getFactionIcon());
                    if(!StringUtils.isBlank(actualUrl)) {
                        agent.setFactionIcon(actualUrl);
                    }
                }
            });
        }
    }

    @SuppressWarnings("java:S125")
    private void readCoreSkillBonus(HakushinAgent agent, JsonNode root) {

        /*
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

        JsonNode coreBonusRoot = root.get(hakushinAPIConfiguration.getProperties().character().coreBonus().key());
        if (coreBonusRoot != null) {
            HashMap<Integer, List<ItemProperty>> propertyBonusMap = new HashMap<>();
            for (int i = 0; i < hakushinAPIConfiguration.getProperties().character().coreBonus().maxLevel(); i++) {
                List<ItemProperty> bonusProperties = new ArrayList<>();
                JsonNode coreBonusNode = coreBonusRoot.get(String.valueOf(i + 1));
                if (coreBonusNode != null) {
                    JsonNode propertyRoot =
                            coreBonusNode.get(hakushinAPIConfiguration.getProperties().character().coreBonus().property());
                    if (propertyRoot != null) {
                        propertyRoot.forEachEntry((k, v) -> {
                            long propertyId =
                                    v.get(hakushinAPIConfiguration.getProperties().character().coreBonus().propertyId()).asLong(0L);
                            String propertyName =
                                    v.get(hakushinAPIConfiguration.getProperties().character().coreBonus().propertyName()).asString(
                                            UNDEFINED_VALUE);
                            String propertyFormat =
                                    v.get(hakushinAPIConfiguration.getProperties().character().coreBonus().propertyFormat()).asString(
                                            UNDEFINED_VALUE);
                            int propertyValue =
                                    v.get(hakushinAPIConfiguration.getProperties().character().coreBonus().propertyValue()).asInt(0);

                            bonusProperties.add(new ItemProperty(propertyId, propertyValue, propertyName,
                                    propertyFormat));
                        });
                    }
                }
                propertyBonusMap.put(i + 1, bonusProperties);
            }
            agent.setCoreSkillBonus(propertyBonusMap);
        }
    }

    @SuppressWarnings("java:S125")
    private void readCoreSkillDescriptions(HakushinAgent agent, JsonNode root) {

        /*
         * "Passive": {
         *     "Level": {
         *       "1411049": {
         *         "Level": 1,
         *         "Id": 1411049,
         *         "Name": [
         *           "Core Passive: Whimsical Wonder",
         *           "Additional Ability: The More the Merrier"
         *         ],
         *         "Desc": [
         *           "Upon entering...",
         *           "When another character in your squad is..."
         *         ],
         *         "ExtraProperty": {
         *
         *         },
         *         "Potential": []
         *       },
         *       "1411050": {
         *         "Level": 2,
         *         "Id": 1411050,
         *         "Name": [
         *           "Core Passive: Whimsical Wonder",
         *           "Additional Ability: The More the Merrier"
         *         ],
         *         "Desc": [
         *           "Upon entering..."
         *           "When another character in your squad is..."
         *         ],
         *         "ExtraProperty": {
         *
         *         },
         *         "Potential": []
         *       },
         */

        JsonNode passiveRoot = root.get(hakushinAPIConfiguration.getProperties().character().passive().key());
        if (passiveRoot != null) {
            HashMap<Integer, HakushinCoreSkill> coreSkillMap = new HashMap<>();
            JsonNode passiveNode =
                    passiveRoot.get(hakushinAPIConfiguration.getProperties().character().passive().property());
            passiveNode.forEachEntry((k, v) -> {
                HakushinCoreSkill coreSkill = new HakushinCoreSkill();

                JsonNode levelNode =
                        v.get(hakushinAPIConfiguration.getProperties().character().passive().propertyLevel());

                if (levelNode == null) {
                    return;
                }

                int levelValue = levelNode.asInt(0);

                JsonNode nameArray =
                        v.get(hakushinAPIConfiguration.getProperties().character().passive().propertyName());
                List<String> names = this.jsonArraytoStringList(nameArray);

                JsonNode descriptionArray =
                        v.get(hakushinAPIConfiguration.getProperties().character().passive().propertyDescription());
                List<String> descriptions = this.jsonArraytoStringList(descriptionArray);

                //we only expect 2 entries here, first one being the core skill and the second being the additional
                if (names.size() > 1 && descriptions.size() > 1) {
                    coreSkill.setCoreName(names.get(0));
                    coreSkill.setAdditionalName(names.get(1));

                    coreSkill.setCoreDescription(descriptions.get(0));
                    coreSkill.setAdditionalDescription(descriptions.get(1));
                }

                coreSkillMap.put(levelValue, coreSkill);
            });

            agent.setCoreSkillDescriptions(coreSkillMap);
        }
    }

    @SuppressWarnings("java:S125")
    private void readSkillDescriptions(HakushinAgent agent, JsonNode root) {

        /*
         * "Skill": {
         *     "Basic": {
         *       "Description": [
         *         {
         *           "Name": "Basic Attack: Tanuki Claws",
         *           "Desc": "Press \u003CIconMap:Icon_Normal\u003E to activate:\nUnleashes...",
         *           "Potential": []
         *         },
         *         {
         *           "Name": "Basic Attack: Tanuki Claws",
         *           "Param": [
         *             {
         *               "Name": "1st-Hit DMG Multiplier",
         *               "Desc": "{Skill:1411001, Prop:1001}",
         *               "Param": {
         *                 "1411001": {
         *                   "Main": 3770,
         *                   "Growth": 350,
         *                   "Format": "%",
         *                   "DamagePercentage": 3770,
         *                   "DamagePercentageGrowth": 350,
         *                   "StunRatio": 4400,
         *                   "StunRatioGrowth": 200,
         *                   "SpRecovery": 14400,
         *                   "SpRecoveryGrowth": 0,
         *                   "FeverRecovery": 110000,
         *                   "FeverRecoveryGrowth": 0,
         *                   "AttributeInfliction": 1618,
         *                   "SpConsume": 0,
         *                   "AttackData": [],
         *                   "RpRecovery": 0,
         *                   "RpRecoveryGrowth": 0,
         *                   "EtherPurify": 3998
         *                 }
         *               },
         *               "Potential": []
         *             }
         */

        JsonNode skillRoot = root.get(hakushinAPIConfiguration.getProperties().character().skill().key());
        if (skillRoot == null) {
            return;
        }

        Map<SkillType, List<HakushinAgentSkill>> skillDescriptions = new EnumMap<>(SkillType.class);
        List<SkillType> skillsToCheck = new ArrayList<>(Arrays.asList(SkillType.values()));
        //do not check the core skill, since that needs special handling
        skillsToCheck.remove(SkillType.CORE_SKILL);

        skillsToCheck.forEach(t -> {
            List<HakushinAgentSkill> skillsOfType = new ArrayList<>();
            String skillKey = this.getPropertyKeyOfSkillType(t);

            if (skillKey == null) {
                return;
            }

            JsonNode skillNode = skillRoot.get(skillKey);

            if (skillNode == null) {
                return;
            }

            JsonNode descriptionRoot =
                    skillNode.get(hakushinAPIConfiguration.getProperties().character().skill().property());

            if (descriptionRoot == null || !descriptionRoot.isArray()) {
                return;
            }

            for (JsonNode skillDescription : descriptionRoot.values()) {
                JsonNode nameNode =
                        skillDescription.get(hakushinAPIConfiguration.getProperties().character().skill().propertyName());
                JsonNode descriptionNode =
                        skillDescription.get(hakushinAPIConfiguration.getProperties().character().skill().propertyDescription());
                JsonNode paramNode =
                        skillDescription.get(hakushinAPIConfiguration.getProperties().character().skill().propertyParam());

                //only record entries where the name and description is set and the parameter map is not
                // (since we are only interested in skill descriptions)
                if (nameNode == null || descriptionNode == null || paramNode != null) {
                    continue;
                }

                skillsOfType.add(new HakushinAgentSkill(nameNode.asString(UNDEFINED_VALUE),
                        descriptionNode.asString(UNDEFINED_VALUE)));
            }

            skillDescriptions.put(t, skillsOfType);
        });

        agent.setSkillDescriptions(skillDescriptions);
    }

    @SuppressWarnings("java:S125")
    private void readTalentDescriptions(HakushinAgent agent, JsonNode root) {

        /*
         * "Talent": {
         *     "1": {
         *       "Level": 1,
         *       "Name": "Lucky Constitution",
         *       "Desc": "Yuzuha regains 30 Energy when...",
         *       "Desc2": "\"Ugh, I'm done for..."
         *     },
         *     "2": {
         *       "Level": 2,
         *       "Name": "Full of Colorful Company",
         *       "Desc": "When Yuzuha's \u003Ccolor=#FFFFFF\u003EEX Special Attack: Cavity Alert...",
         *       "Desc2": "\"Kama, you've never left my sight..."
         *     },
         */

        JsonNode talentRoot = root.get(hakushinAPIConfiguration.getProperties().character().talent().key());
        if(talentRoot == null) {
            return;
        }

        Map<Integer, HakushinMindscape> mindscapeMap = new HashMap<>();

        talentRoot.forEach(t -> {
            JsonNode levelNode = t.get(hakushinAPIConfiguration.getProperties().character().talent().propertyLevel());
            JsonNode nameNode = t.get(hakushinAPIConfiguration.getProperties().character().talent().propertyName());
            JsonNode descriptionNode =
                    t.get(hakushinAPIConfiguration.getProperties().character().talent().propertyDescription());

            if(levelNode == null || nameNode == null || descriptionNode == null) {
                return;
            }

            int talentLevel = levelNode.asInt(0);
            String talentName = nameNode.asString(UNDEFINED_VALUE);
            String talentDescription = descriptionNode.asString(UNDEFINED_VALUE);

            mindscapeMap.put(talentLevel, new HakushinMindscape(talentName, talentDescription));
        });

        agent.setMindscapeDescriptions(mindscapeMap);
    }

    private String getPropertyKeyOfSkillType(SkillType type) {
        return switch (type) {
            case BASIC_ATTACK -> hakushinAPIConfiguration.getProperties().character().skill().basicAttack();
            case SPECIAL_ATTACK -> hakushinAPIConfiguration.getProperties().character().skill().specialAttack();
            case DASH -> hakushinAPIConfiguration.getProperties().character().skill().dash();
            case ULTIMATE -> hakushinAPIConfiguration.getProperties().character().skill().ultimate();
            case ASSIST -> hakushinAPIConfiguration.getProperties().character().skill().assist();
            default -> null;
        };
    }

    /**
     * try to convert this JSON node to its string representation
     *
     * @param node the node to convert
     * @return a list containing all entries of this node
     */
    private List<String> jsonArraytoStringList(JsonNode node) {
        List<String> resultList = new ArrayList<>();
        if (node != null && node.isArray()) {
            node.forEach(t -> resultList.add(t.asString(UNDEFINED_VALUE)));
        }

        return resultList;
    }
}
