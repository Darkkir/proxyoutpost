package me.darkkir3.proxyoutpost.model.output;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

public class PropertyOutput {

    /**
     * the name of this property in the api
     */
    private String internalName;

    /**
     * regex that describes how to display this property
     */
    private String format;

    /**
     * the translated name of this property
     */
    private String translatedName;

    @JsonProperty("Name")
    public void setInternalName(String value) {
        this.internalName = value;
    }

    @JsonIgnore
    public String getInternalName() {
        return this.internalName;
    }

    @JsonProperty("Format")
    public void setFormat(String value) {
        this.format = value;
    }

    @JsonIgnore
    public String getFormat() {
        return this.format;
    }

    @JsonProperty("name")
    public String getTranslatedName() {
        return this.translatedName;
    }

    @JsonIgnore
    public void setTranslatedName(String value) {
        this.translatedName = value;
    }
}
