package me.darkkir3.proxyoutpost.model.db;

/**
 * Lists all possible drive disc rarities
 */
public enum DriveDiscRarity {

    UNKNOWN(0),
    B_RANK(2),
    A_RANK(3),
    S_RANK(4);

    private final int index;

    private DriveDiscRarity(int index) {
        this.index = index;
    }

    public int getIndex() {
        return this.index;
    }
}
