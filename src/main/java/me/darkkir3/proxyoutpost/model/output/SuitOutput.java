package me.darkkir3.proxyoutpost.model.output;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * represents a drive disc set (also called suit)
 */
public class SuitOutput {

    /**
     * the rarity of this suit
     */
    @JsonProperty("Rarity")
    public int rarity;

    /**
     * the actual id of the suit
     */
    @JsonProperty("SuitId")
    public int suitId;

    /**
     * the icon url of this set (e.g. /ui/zzz/SuitYunkuiTales.png)
     */
    @JsonProperty("Icon")
    public String icon;

    /**
     * the suit name (e.g. EquipmentSuit_33100_name)
     */
    @JsonProperty("Name")
    public String name;


}
