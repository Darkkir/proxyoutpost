package me.darkkir3.proxyoutpost.equipment;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Map;

public class DriveDiscSuit {

    /**
     * the translated or untranslated icon of this disc set
     */
    private String icon;

    /**
     * the translated or untranslated name of this disc set
     */
    private String name;

    /**
     * the 2 pc set bonus for this disc set
     */
    private Map<Long, Integer> setBonusProps;

    @JsonProperty("Icon")
    public String getIcon() {
        return this.icon;
    }

    @JsonProperty("Icon")
    public void setIcon(String icon) {
        this.icon = icon;
    }

    @JsonProperty("SetName")
    public String getName() {
        return this.name;
    }

    @JsonProperty("Name")
    public void setName(String name) {
        this.name = name;
    }

    @JsonIgnore
    public Map<Long, Integer> getSetBonusProps() {
        return this.setBonusProps;
    }

    @JsonProperty("SetBonusProps")
    public void setSetBonusProps(Map<Long, Integer> setBonusProps) {
        this.setBonusProps = setBonusProps;
    }
}
