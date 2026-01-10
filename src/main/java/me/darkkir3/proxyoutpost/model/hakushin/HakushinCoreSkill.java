package me.darkkir3.proxyoutpost.model.hakushin;

public class HakushinCoreSkill {

    private String coreName;
    private String coreDescription;
    private String additionalName;
    private String additionalDescription;

    public HakushinCoreSkill() {}

    public HakushinCoreSkill(String coreName, String coreDescription, String additionalName, String additionalDescription) {
        this.coreName = coreName;
        this.coreDescription = coreDescription;
        this.additionalName = additionalName;
        this.additionalDescription = additionalDescription;
    }

    public String getCoreName() {
        return coreName;
    }

    public void setCoreName(String coreName) {
        this.coreName = coreName;
    }

    public String getCoreDescription() {
        return coreDescription;
    }

    public void setCoreDescription(String coreDescription) {
        this.coreDescription = coreDescription;
    }

    public String getAdditionalName() {
        return additionalName;
    }

    public void setAdditionalName(String additionalName) {
        this.additionalName = additionalName;
    }

    public String getAdditionalDescription() {
        return additionalDescription;
    }

    public void setAdditionalDescription(String additionalDescription) {
        this.additionalDescription = additionalDescription;
    }
}
