package me.darkkir3.proxyoutpost.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("classpath:hoyolab.properties")
public class HoyolabAPIConfiguration {

    @Value("${hoyolab.icon-role.prefix}")
    private String iconRolePrefix;
    @Value("${hoyolab.icon-role.suffix}")
    private String iconRoleSuffix;

    /**
     * @return the prefix of the icon role url for agents
     */
    public String getIconRolePrefix() {
        return iconRolePrefix;
    }

    /**
     * @return #the suffix of the icon role url for agents
     */
    public String getIconRoleSuffix() {
        return iconRoleSuffix;
    }
}
