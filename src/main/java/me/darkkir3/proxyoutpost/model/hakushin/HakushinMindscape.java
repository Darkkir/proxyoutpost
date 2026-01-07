package me.darkkir3.proxyoutpost.model.hakushin;

import java.util.List;

public class HakushinMindscape {
    private int level;
    private String name;
    private List<String> descriptions;

    public HakushinMindscape(int level, String name, List<String> descriptions) {
        this.level = level;
        this.name = name;
        this.descriptions = descriptions;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getDescriptions() {
        return descriptions;
    }

    public void setDescriptions(List<String> descriptions) {
        this.descriptions = descriptions;
    }
}
