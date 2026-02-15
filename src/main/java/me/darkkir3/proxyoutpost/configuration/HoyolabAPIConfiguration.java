package me.darkkir3.proxyoutpost.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import java.util.Map;

@Configuration
@PropertySource("classpath:hoyolab.properties")
public class HoyolabAPIConfiguration {

    @Value("${hoyolab.icon-role.prefix}")
    private String iconRolePrefix;
    @Value("${hoyolab.icon-role.suffix}")
    private String iconRoleSuffix;
    @Value("${hoyowiki.base-url}")
    private String baseUrl;
    @Value("${hoyowiki.agent-page-url}")
    private String agentPageUrl;
    @Value("${hoyowiki.agent-list-url}")
    private String agentListUrl;
    @Value("#{${hoyowiki.default-header}}")
    private Map<String, String> defaultHeader;
    @Value("${hoyowiki.data-key}")
    private String dataKey;
    @Value("${hoyowiki.page-key}")
    private String pageKey;
    @Value("${hoyowiki.name-key}")
    private String nameKey;
    @Value("${hoyowiki.desc-key}")
    private String descKey;
    @Value("${hoyowiki.icon-url-key}")
    private String iconUrlKey;
    @Value("${hoyowiki.header-image-url-key}")
    private String headerImageUrlKey;
    @Value("${hoyowiki.filter-values-key}")
    private String filterValuesKey;
    @Value("${hoyowiki.value-types-key}")
    private String valueTypesKey;
    @Value("${hoyowiki.value-key}")
    private String valueKey;
    @Value("${hoyowiki.icon-key}")
    private String iconKey;
    @Value("${hoyowiki.agent-specialties-key}")
    private String agentSpecialtiesKey;
    @Value("${hoyowiki.agent-stats-key}")
    private String agentStatsKey;
    @Value("${hoyowiki.agent-faction-key}")
    private String agentFactionKey;
    @Value("${hoyowiki.module-key}")
    private String moduleKey;
    @Value("${hoyowiki.mindscape-cinema-key}")
    private String mindscapeCinemaKey;
    @Value("${hoyowiki.summary-list-key}")
    private String summaryListKey;

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

    /**
     * @return HoyoWiki base url
     */
    public String getBaseUrl() {
        return baseUrl;
    }

    /**
     * @return endpoint for retrieving HoyoWiki agent pages
     */
    public String getAgentPageUrl() {
        return agentPageUrl;
    }

    /**
     * @return endpoint for retrieving HoyoWiki lists
     */
    public String getAgentListUrl() {
        return agentListUrl;
    }

    /**
     * @return default header to include in every HoyoWiki request
     */
    public Map<String, String> getDefaultHeader() {
        return defaultHeader;
    }

    /**
     * @return JsonNode key to use when querying HoyoWiki for agent data
     */
    public String getDataKey() {
        return dataKey;
    }

    /**
     * @return JsonNode key to use when querying HoyoWiki for agent data
     */
    public String getPageKey() {
        return pageKey;
    }

    /**
     * @return JsonNode key to use when querying HoyoWiki for agent data
     */
    public String getNameKey() {
        return nameKey;
    }

    /**
     * @return JsonNode key to use when querying HoyoWiki for agent data
     */
    public String getDescKey() {
        return descKey;
    }

    /**
     * @return JsonNode key to use when querying HoyoWiki for agent data
     */
    public String getIconUrlKey() {
        return iconUrlKey;
    }

    /**
     * @return JsonNode key to use when querying HoyoWiki for agent data
     */
    public String getHeaderImageUrlKey() {
        return headerImageUrlKey;
    }

    /**
     * @return JsonNode key to use when querying HoyoWiki for agent data
     */
    public String getFilterValuesKey() {
        return filterValuesKey;
    }

    /**
     * @return JsonNode key to use when querying HoyoWiki for agent data
     */
    public String getValueTypesKey() {
        return valueTypesKey;
    }

    /**
     * @return JsonNode key to use when querying HoyoWiki for agent data
     */
    public String getValueKey() {
        return valueKey;
    }

    /**
     * @return JsonNode key to use when querying HoyoWiki for agent data
     */
    public String getIconKey() {
        return iconKey;
    }

    /**
     * @return JsonNode key to use when querying HoyoWiki for agent data
     */
    public String getAgentSpecialtiesKey() {
        return agentSpecialtiesKey;
    }

    /**
     * @return JsonNode key to use when querying HoyoWiki for agent data
     */
    public String getAgentStatsKey() {
        return agentStatsKey;
    }

    /**
     * @return JsonNode key to use when querying HoyoWiki for agent data
     */
    public String getAgentFactionKey() {
        return agentFactionKey;
    }

    /**
     * @return JsonNode key to use when querying HoyoWiki for agent data
     */
    public String getModuleKey() {
        return moduleKey;
    }

    /**
     * @return JsonNode key to use when querying HoyoWiki for agent data
     */
    public String getMindscapeCinemaKey() {
        return mindscapeCinemaKey;
    }

    /**
     * @return JsonNode key to use when querying HoyoWiki for agent data
     */
    public String getSummaryListKey() {
        return summaryListKey;
    }
}
