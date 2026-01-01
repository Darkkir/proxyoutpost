package me.darkkir3.proxyoutpost.utils.transformer;

import me.darkkir3.proxyoutpost.configuration.HoyolabAPIConfiguration;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

@Component
public class HoyolabImageUrlTransformer extends AbstractImageUrlTansformer {
    private final HoyolabAPIConfiguration hoyolabAPIConfiguration;

    public HoyolabImageUrlTransformer(HoyolabAPIConfiguration hoyolabAPIConfiguration) {
        this.hoyolabAPIConfiguration = hoyolabAPIConfiguration;
    }

    @Override
    public String transformAgentImage(Long agentId, String original) {
        if(!StringUtils.isEmpty(original) && agentId != null) {
            return hoyolabAPIConfiguration.getIconRolePrefix() + agentId + hoyolabAPIConfiguration.getIconRoleSuffix();
        }

        return original;
    }
}
