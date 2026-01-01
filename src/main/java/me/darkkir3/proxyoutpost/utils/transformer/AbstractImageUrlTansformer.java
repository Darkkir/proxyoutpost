package me.darkkir3.proxyoutpost.utils.transformer;

public abstract class AbstractImageUrlTansformer implements ImageUrlTransformer {

    public String transformCallingCardUrl(String original) {
        return original;
    }

    public String transformProfilePicture(String original) {
        return original;
    }

    public String transformSuitIcon(String original) {
        return original;
    }

    public String transformWeaponImage(String original) {
        return original;
    }

    public String transformAgentCircleIcon(Long agentId, String original) {
        return original;
    }

    public String transformAgentImage(Long agentId, String original) {
        return original;
    }
}
