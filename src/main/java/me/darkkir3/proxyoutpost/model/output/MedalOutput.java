package me.darkkir3.proxyoutpost.model.output;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

public class MedalOutput {

    /**
     * the internal name of this medal (e.g. 'MedalName7001')
     */
    private String name;

    /**
     * the untranslated icon url for this medal
     */
    private String icon;

    /**
     * not quite sure what this is for (ranges from MedalTipsNum1-5)
     */
    private String tipNum;

    /**
     * Another icon url to put on top of this badge
     */
    private String prefixIcon;

    @JsonProperty("name")
    public String getName() {
        return name;
    }

    @JsonProperty("Name")
    public void setName(String name) {
        this.name = name;
    }

    @JsonProperty("icon")
    public String getIcon() {
        return icon;
    }

    @JsonProperty("Icon")
    public void setIcon(String icon) {
        this.icon = icon;
    }

    @JsonIgnore
    public String getTipNum() {
        return tipNum;
    }

    @JsonProperty("TipNum")
    public void setTipNum(String tipNum) {
        this.tipNum = tipNum;
    }

    @JsonProperty("prefixIcon")
    public String getPrefixIcon() {
        return prefixIcon;
    }

    @JsonProperty("PrefixIcon")
    public void setPrefixIcon(String prefixIcon) {
        this.prefixIcon = prefixIcon;
    }
}
