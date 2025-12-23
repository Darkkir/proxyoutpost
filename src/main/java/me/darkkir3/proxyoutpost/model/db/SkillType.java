package me.darkkir3.proxyoutpost.model.db;

/**
 * Lists all possible skill types of an agent
 */
public enum SkillType {

    BASIC_ATTACK(0),
    SPECIAL_ATTACK(1),
    DASH(2),
    ULTIMATE(3),
    CORE_SKILL(5),
    ASSIST(6);

    private final int index;

    private SkillType(int index) {
        this.index = index;
    }

    public int getIndex() {
        return this.index;
    }
}
