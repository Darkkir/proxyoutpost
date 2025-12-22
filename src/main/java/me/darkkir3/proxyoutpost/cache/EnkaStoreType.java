package me.darkkir3.proxyoutpost.cache;

/**
 * Lists all configuration stores that enka api uses
 */
public enum EnkaStoreType {
    AVATARS("avatars.json"),
    EQUIPMENTS("equipments.json"),
    LOCS("locs.json"),
    MEDALS("medals.json"),
    NAMECARDS("namecards.json"),
    PFPS("pfps.json"),
    PROPERTY("property.json"),
    TITLES("titles.json"),
    WEAPONS("weapons.json");

    private final String fileName;

    EnkaStoreType(String fileName) {
        this.fileName = fileName;
    }

    public String getFileName() {
        return this.fileName;
    }
}
