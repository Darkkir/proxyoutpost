package me.darkkir3.proxyoutpost.utils;

import me.darkkir3.proxyoutpost.configuration.HoyolabAPIConfiguration;
import me.darkkir3.proxyoutpost.model.hoyowiki.HoyoMindscape;
import me.darkkir3.proxyoutpost.model.hoyowiki.HoyoWikiAgent;
import org.apache.commons.lang3.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;
import tools.jackson.databind.JsonNode;
import tools.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class HoyoWikiScraper {

    private static final Logger log = LoggerFactory.getLogger(HoyoWikiScraper.class);
    private final HoyolabAPIConfiguration hoyolabAPIConfiguration;

    private Map<String, Integer> agentNameToEntryIdMap = null;

    public HoyoWikiScraper(HoyolabAPIConfiguration hoyolabAPIConfiguration) {
        this.hoyolabAPIConfiguration = hoyolabAPIConfiguration;
    }

    public void clearAgenListCache() {
        this.agentNameToEntryIdMap = null;
    }

    /**
     * replace all special chars to easier find matches with the agent name
     * @param name
     * @return
     */
    private String normalizeAgentName(String name) {
        return name.toLowerCase().replaceAll("[^a-z0-9]", "");
    }

    private Map<String, Integer> fetchAgentNameToEntryIdMap(int currentPage, ObjectMapper mapper) {

        //TODO: make those values configurable including the keys of the json nodes
        int menuId = 8;
        int pageSize = 50;
        int pageNum = currentPage + 1;

        Map<String, Object> payload = Map.of(
                "menu_id", menuId,
                "page_size", pageSize,
                "page_num", pageNum
        );

        String body = "";
        body = mapper.writeValueAsString(payload);

        String baseUrl = hoyolabAPIConfiguration.getBaseUrl();
        String listEndpoint = hoyolabAPIConfiguration.getAgentListUrl();
        Map<String, String> defaultHeaders = hoyolabAPIConfiguration.getDefaultHeader();

        String[] headerList = new String[defaultHeaders.size() * 2];
        int headerIndex = 0;
        for(Map.Entry<String, String> entry : defaultHeaders.entrySet()) {
            headerList[headerIndex++] = entry.getKey();
            headerList[headerIndex++] = entry.getValue();
        }

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(baseUrl + listEndpoint))
                .headers(headerList)
                .POST(HttpRequest.BodyPublishers.ofString(body))
                .build();

        HttpResponse<String> response = null;
        try (HttpClient client = HttpClient.newHttpClient())
        {
            response =
                    client.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException e) {
            log.error("Failed to fetch agent list using body {} from hoyowiki: {} - {}", body,
                    baseUrl + listEndpoint, e.getMessage());
        }
        catch (InterruptedException e) {
            log.error("Interrupted when fetching using body {} from hoyowiki: {} - {}", body,
                    baseUrl + listEndpoint, e.getMessage());
            Thread.currentThread().interrupt();
        }

        Map<String, Integer> result = new HashMap<>();
        if(response != null) {
            JsonNode root = null;
            root = mapper.readTree(response.body());

            JsonNode list = root.path("data").path("list");
            JsonNode total = root.path("data").path("total");
            int totalEntries = total.asInt();

            for (JsonNode entry : list) {
                String name = entry.path("name").asString();
                int entryId = entry.path("entry_page_id").asInt();
                result.put(this.normalizeAgentName(name), entryId);
            }

            //do we still have values to display?
            if(totalEntries - (pageSize * pageNum) > 0)  {
                result.putAll(fetchAgentNameToEntryIdMap(currentPage + 1, mapper));
            }
        }

        return result;
    }

    public HoyoWikiAgent scrapeAgentById(String agentName) {
        ObjectMapper mapper = new ObjectMapper();

        int agentId = -1;

        if(this.agentNameToEntryIdMap == null) {
            this.agentNameToEntryIdMap = this.fetchAgentNameToEntryIdMap(0, mapper);
        }

        String normalizedName = this.normalizeAgentName(agentName);

        if(this.agentNameToEntryIdMap != null) {
            for(Map.Entry<String, Integer> entry : this.agentNameToEntryIdMap.entrySet()) {
                if(entry.getKey().contains(normalizedName) ||
                        normalizedName.contains(entry.getKey())) {
                    agentId = entry.getValue();
                    break;
                }
            }
        }

        String baseUrl = hoyolabAPIConfiguration.getBaseUrl();
        String pageUri = hoyolabAPIConfiguration.getAgentPageUrl();
        Map<String, String> defaultHeaders = hoyolabAPIConfiguration.getDefaultHeader();

        RestClient.Builder restClientBuilder = RestClient.builder()
                .baseUrl(baseUrl);

        for(Map.Entry<String, String> entry : defaultHeaders.entrySet()) {
            restClientBuilder = restClientBuilder.defaultHeader(entry.getKey(), entry.getValue());
        }

        RestClient restClient = restClientBuilder.build();

        String dataKey = hoyolabAPIConfiguration.getDataKey();
        String pageKey = hoyolabAPIConfiguration.getPageKey();
        String nameKey = hoyolabAPIConfiguration.getNameKey();
        String descKey = hoyolabAPIConfiguration.getDescKey();
        String iconUrlKey = hoyolabAPIConfiguration.getIconUrlKey();
        String headerImgUrlKey = hoyolabAPIConfiguration.getHeaderImageUrlKey();
        String filterValuesKey = hoyolabAPIConfiguration.getFilterValuesKey();
        String valueTypesKey = hoyolabAPIConfiguration.getValueTypesKey();
        String valueKey = hoyolabAPIConfiguration.getValueKey();
        String iconKey = hoyolabAPIConfiguration.getIconKey();
        String agentSpecialtiesKey = hoyolabAPIConfiguration.getAgentSpecialtiesKey();
        String agentStatsKey = hoyolabAPIConfiguration.getAgentStatsKey();
        String agentFactionKey = hoyolabAPIConfiguration.getAgentFactionKey();
        String moduleKey = hoyolabAPIConfiguration.getModuleKey();
        String mindscapeCinemaKey = hoyolabAPIConfiguration.getMindscapeCinemaKey();
        String summaryListKey = hoyolabAPIConfiguration.getSummaryListKey();



        JsonNode root = restClient.get()
                .uri(pageUri, agentId)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .body(JsonNode.class);

        if(root == null) {
            log.error("Missing/invalid root node");
            return null;
        }

        JsonNode dataNode = root.get(dataKey);

        if(dataNode == null) {
            log.error("Missing/invalid root/{} node", dataKey);
            return null;
        }

        JsonNode pageNode = dataNode.get(pageKey);

        if(pageNode == null) {
            log.error("Missing/invalid root/{}/{} node", dataKey, pageKey);
            return null;
        }

        JsonNode fullName = pageNode.get(nameKey);
        if(fullName == null) {
            logMissingNode(nameKey, pageNode);
            return null;
        }

        JsonNode description = pageNode.get(descKey);
        if(description == null) {
            logMissingNode(descKey, pageNode);
            return null;
        }

        JsonNode iconUrl = pageNode.get(iconUrlKey);
        if(iconUrl == null) {
            logMissingNode(iconUrlKey, pageNode);
            return null;
        }

        JsonNode headerImgUrl = pageNode.get(headerImgUrlKey);
        if(headerImgUrl == null) {
            logMissingNode(headerImgUrlKey, pageNode);
            return null;
        }

        JsonNode filterValues = pageNode.get(filterValuesKey);
        if(filterValues == null) {
            logMissingNode(filterValuesKey, pageNode);
            return null;
        }

        //agent specialty (e.g. attack / defense / support...)
        Map<String, String> specialtyMap = findAgentStats(agentSpecialtiesKey, filterValues, valueTypesKey, valueKey, iconKey);
        //agent element type (e.g. electric / fire / ether...)
        Map<String, String> typeMap = findAgentStats(agentStatsKey, filterValues, valueTypesKey, valueKey, iconKey);
        //agent faction (e.g. belobog industries / spook shack / ...)
        Map<String, String> factionMap = findAgentStats(agentFactionKey, filterValues, valueTypesKey, valueKey, iconKey);

        JsonNode modulesNode = pageNode.get(moduleKey);

        List<HoyoMindscape> mindscapes = new ArrayList<>();

        if(modulesNode != null) {
            List<JsonNode> mindscapeModuleData = findAgentModuleData(mapper, mindscapeCinemaKey, summaryListKey,
                    pageNode.get(moduleKey));

            mindscapeModuleData.forEach(m -> mindscapes.add(HoyoMindscape.fromJsonNode(fullName.asText(), m)));
        }

        return new HoyoWikiAgent(
                fullName.asText(),
                description.asText(),
                iconUrl.asText(),
                headerImgUrl.asText(),
                specialtyMap,
                typeMap,
                factionMap,
                mindscapes
        );
    }

    private void logMissingNode(String nodeName, JsonNode parentNode) {
        if(log.isErrorEnabled()) {
            log.error("Missing {} node in page node {}", nodeName, parentNode.toPrettyString());
        }
    }

    private Map<String, String> findAgentStats(String statName, JsonNode filterValues, String valueTypesKey, String valueKey, String iconKey) {
        Map<String, String> statMap = new HashMap<>();
        JsonNode statRoot = filterValues.get(statName);

        if(statRoot == null) {
            return statMap;
        }

        JsonNode valueTypes = statRoot.get(valueTypesKey);

        if(valueTypes == null || !valueTypes.isArray()) {
            return statMap;
        }

        JsonNode child = null;
        for(int i = 0; i < valueTypes.size(); i++) {
            child = valueTypes.get(i);
            statMap.put(child.get(valueKey).asText(), child.get(iconKey).asText());
        }

        return statMap;
    }

    private List<JsonNode> findAgentModuleData(ObjectMapper mapper, String moduleName, String componentName,
                                               JsonNode modules) {
        List<JsonNode> moduleList = new ArrayList<>();
        if(modules == null || !modules.isArray()) {
            return moduleList;
        }

        for(int i = 0; i < modules.size(); i++) {
            JsonNode child = modules.get(i);
            JsonNode childName = child.get("name");

            if(childName == null) {
                continue;
            }

            if(!Strings.CS.equals(childName.asText(), moduleName)) {
                continue;
            }

            JsonNode components = child.get("components");
            if(components == null || !components.isArray()) {
                continue;
            }

            for(int j = 0; j < components.size(); j++) {
                JsonNode component = components.get(j);
                JsonNode componentId = component.get("component_id");

                if(componentId == null) {
                    continue;
                }

                if(!Strings.CS.equals(componentId.asText(), componentName)) {
                    continue;
                }

                JsonNode data = component.get("data");

                if(data == null) {
                    continue;
                }

                String dataValue = data.asText();
                JsonNode listNode = null;
                listNode = mapper.readTree(dataValue);

                if(listNode == null) {
                    continue;
                }

                JsonNode entryNode = listNode.get("list");
                if(entryNode == null || !entryNode.isArray()) {
                    continue;
                }

                for(JsonNode listEntry : entryNode) {
                    moduleList.add(listEntry);
                }
            }
        }

        return moduleList;
    }
}
