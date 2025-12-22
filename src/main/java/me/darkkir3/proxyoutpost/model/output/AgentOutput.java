package me.darkkir3.proxyoutpost.model.output;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.Map;

public class AgentOutput {

    /**
     * the agent name (e.g. 'Avatar_Female_Size02_Anbi')
     */
    @JsonProperty("Name")
    public String name;

    /**
     * the rarity of the agent
     * <br>3 = A-rank
     * <br>4 = S-rank
     */
    @JsonProperty("Rarity")
    public int rarity;

    /**
     * agent profession type (e.g. 'Stun')
     */
    @JsonProperty("ProfessionType")
    public String professionType;

    /**
     * list of elements this agent belongs to
     * (e.g. 'AuricEther', 'Ether' for YiXuan)
     */
    @JsonProperty("ElementTypes")
    public ArrayList<String> elementTypes;

    /**
     * url to full character display image
     */
    @JsonProperty("Image")
    public String image;

    /**
     * url to character circle image
     */
    @JsonProperty("CircleIcon")
    public String circleIcon;

    /**
     * the id of this agents signature weapon
     */
    @JsonProperty("WeaponId")
    public int weaponId;

    /**
     * the color of the skill buttons for this agent
     */
    @JsonProperty("AccentColor")
    public String accentColor;

    /**
     *  the color of the mindscape toggle for this agent
     */
    @JsonProperty("MindscapeColor")
    public String mindscapeColor;

    @JsonProperty("Colors")
    public void setColors(Map<String, String> colors) {
    this.accentColor = colors.get("Accent");
    this.mindscapeColor = colors.get("Mindscape");
    }
}
