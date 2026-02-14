package me.darkkir3.proxyoutpost.model.output;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import me.darkkir3.proxyoutpost.model.db.AgentRarity;

import java.util.*;

/**
 * generic non-player-specific agent info
 */
public class AgentOutput {

    private static final String ACCENT_COLOR = "Accent";
    private static final String MINDSCAPE_COLOR = "Mindscape";

    /**
     * the agent name (e.g. 'Avatar_Female_Size02_Anbi')
     */
    @JsonIgnore
    private String name;

    /**
     *  the agent id
     */
    @JsonIgnore
    private Long agentId;

    /**
     * the rarity of the agent
     * <br>3 = A-rank
     * <br>4 = S-rank
     */
    @JsonIgnore
    private int rarity;

    /**
     * agent profession type (e.g. 'Stun')
     */
    @JsonIgnore
    private String professionType;

    /**
     * list of elements this agent belongs to
     * (e.g. 'AuricEther', 'Ether' for YiXuan)
     */
    @JsonIgnore
    private List<String> elementTypes;

    /**
     * url to full character display image
     */
    @JsonIgnore
    private String image;

    /**
     * url to character circle image
     */
    @JsonIgnore
    private String circleIcon;

    /**
     * the id of this agents signature weapon
     */
    @JsonIgnore
    private int weaponId;

    /**
     * the color of the skill buttons for this agent
     */
    @JsonIgnore
    private String accentColor;

    /**
     *  the color of the mindscape toggle for this agent
     */
    @JsonIgnore
    private String mindscapeColor;

    /**
     * agent base stats at lvl 0
     */
    @JsonIgnore
    private Map<String, String> baseProperties;

    /**
     * agent stat increases based on agent level
     */
    @JsonIgnore
    private Map<String, String> growthProperties;

    /**
     * agent stat increases based on promotion level
     */
    @JsonIgnore
    private List<Map<String, String>> promotionProperties;

    /**
     * agent stat increases based on the level of the core skill
     */
    @JsonIgnore
    private List<Map<String, String>> coreEnhancementProperties;

    @JsonIgnore
    private List<Long> highLightProperties;

    @JsonProperty("agentId")
    public Long getAgentId() {
        return agentId;
    }

    @JsonProperty("AgentId")
    public void setAgentId(Long agentId) {
        this.agentId = agentId;
    }

    @JsonProperty("elementTypes")
    public List<String> getElementTypes() {
        return elementTypes;
    }

    @JsonProperty("ElementTypes")
    public void setElementTypes(List<String> elementTypes) {
        this.elementTypes = elementTypes;
    }

    @JsonProperty("Colors")
    public void setColors(Map<String, String> colors) {
    this.accentColor = colors.get(ACCENT_COLOR);
    this.mindscapeColor = colors.get(MINDSCAPE_COLOR);
    }

    /**
     * @return the id of this agents signature weapon
     */
    @JsonIgnore
    public int getWeaponId() {
        return this.weaponId;
    }

    /**
     * set the id of this agents signature weapon
     */
    @JsonProperty("WeaponId")
    public void setWeapponId(int value) {
        this.weaponId = value;
    }

    /**
     * set the rarity of the agent
     * <br>3 = A-rank
     * <br>4 = S-rank
     */
    @JsonProperty("Rarity")
    public void setRarity(int value) {
        this.rarity = value;
    }

    /**
     * @return the translated rarity of this agent
     */
    @JsonProperty("rarity")
    public String getRarity() {
        Optional<AgentRarity> agentRarity = Arrays.stream(AgentRarity.values()).filter(t ->
                Objects.equals(this.rarity, t.getIndex())).findFirst();

        return agentRarity.map(Enum::name).orElse(null);
    }

    @JsonIgnore
    public Map<String, String> getBaseProperties() {
        return baseProperties;
    }

    @JsonProperty("BaseProps")
    public void setBaseProperties(Map<String, String> baseProperties) {
        this.baseProperties = baseProperties;
    }

    @JsonIgnore
    public Map<String, String> getGrowthProperties() {
        return growthProperties;
    }

    @JsonProperty("GrowthProps")
    public void setGrowthProperties(Map<String, String> growthProperties) {
        this.growthProperties = growthProperties;
    }

    @JsonIgnore
    public List<Map<String, String>> getPromotionProperties() {
        return promotionProperties;
    }

    @JsonProperty("PromotionProps")
    public void setPromotionProperties(List<Map<String, String>> promotionProperties) {
        this.promotionProperties = promotionProperties;
    }

    @JsonIgnore
    public List<Map<String, String>> getCoreEnhancementProperties() {
        return coreEnhancementProperties;
    }

    @JsonProperty("CoreEnhancementProps")
    public void setCoreEnhancementProperties(List<Map<String, String>> coreEnhancementProperties) {
        this.coreEnhancementProperties = coreEnhancementProperties;
    }

    @JsonProperty("highlightProps")
    public List<Long> getHighLightProperties() {
        return highLightProperties;
    }

    @JsonProperty("HighlightProps")
    public void setHighLightProperties(List<Long> highLightProperties) {
        this.highLightProperties = highLightProperties;
    }

    @JsonProperty("name")
    public String getName() {
        return name;
    }

    @JsonProperty("Name")
    public void setName(String name) {
        this.name = name;
    }

    @JsonProperty("professionType")
    public String getProfessionType() {
        return professionType;
    }

    @JsonProperty("ProfessionType")
    public void setProfessionType(String professionType) {
        this.professionType = professionType;
    }

    @JsonProperty("image")
    public String getImage() {
        return image;
    }

    @JsonProperty("Image")
    public void setImage(String image) {
        this.image = image;
    }

    @JsonProperty("circleIcon")
    public String getCircleIcon() {
        return circleIcon;
    }

    @JsonProperty("CircleIcon")
    public void setCircleIcon(String circleIcon) {
        this.circleIcon = circleIcon;
    }

    @JsonProperty("mindscapeColor")
    public String getMindscapeColor() {
        return mindscapeColor;
    }

    @JsonProperty("MindscapeColor")
    public void setMindscapeColor(String mindscapeColor) {
        this.mindscapeColor = mindscapeColor;
    }

    @JsonProperty("accentColor")
    public String getAccentColor() {
        return accentColor;
    }

    @JsonProperty("AccentColor")
    public void setAccentColor(String accentColor) {
        this.accentColor = accentColor;
    }
}
