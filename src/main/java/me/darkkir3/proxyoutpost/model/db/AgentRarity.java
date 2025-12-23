package me.darkkir3.proxyoutpost.model.db;

/**
 * Lists all possible agent rarities
 */
public enum AgentRarity {

    A_RANK(3),
    S_RANK(4);

    private final int index;

    private AgentRarity(int index) {
        this.index = index;
    }

    public int getIndex() {
        return this.index;
    }
}
