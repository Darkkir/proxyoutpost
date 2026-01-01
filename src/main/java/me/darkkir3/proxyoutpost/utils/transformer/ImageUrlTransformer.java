package me.darkkir3.proxyoutpost.utils.transformer;

public interface ImageUrlTransformer {

    /**
     * Transform the image url of the profile namecard
     * @param original the original url
     * @return the transformed url
     */
    public String transformCallingCardUrl(String original);

    /**
     * Transform the image url of the profile avatar
     * @param original the original url
     * @return the transformed url
     */
    public String transformProfilePicture(String original);

    /**
     * Transform the suit icon url (drive disc set)
     * @param original the original url
     * @return the transformed url
     */
    public String transformSuitIcon(String original);

    /**
     * Transform the image of the weapon
     * @param original the original url
     * @return the transformed url
     */
    public String transformWeaponImage(String original);

    /**
     * Transform the image of the agent circle icon
     * @param agentId the internal agent id
     * @param original the original url
     * @return the transformed url
     */
    public String transformAgentCircleIcon(Long agentId, String original);

    /**
     * Transform the image url of the agent preview icon
     * @param agentId the internal agent id
     * @param original the original url
     * @return the transformed url
     */
    public String transformAgentImage(Long agentId, String original);
}
