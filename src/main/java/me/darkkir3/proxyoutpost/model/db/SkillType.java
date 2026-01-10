package me.darkkir3.proxyoutpost.model.db;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Lists all possible skill types of an agent
 */
public enum SkillType {

    @JsonProperty("basic_attack")
    BASIC_ATTACK(0),
    @JsonProperty("special_attack")
    SPECIAL_ATTACK(1),
    @JsonProperty("dash")
    DASH(2),
    @JsonProperty("ultimate")
    ULTIMATE(3),
    @JsonProperty("core_skill")
    CORE_SKILL(5),
    @JsonProperty("assist")
    ASSIST(6);

    private final int index;

    private SkillType(int index) {
        this.index = index;
    }

    public int getIndex() {
        return this.index;
    }
}
