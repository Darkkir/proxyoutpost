package me.darkkir3.proxyoutpost.model.hoyowiki;

import tools.jackson.databind.JsonNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public record HoyoMindscape(
        String name,
        String description) {

    private static final Logger log = LoggerFactory.getLogger(HoyoMindscape.class);

    public static HoyoMindscape fromJsonNode(String agentName, JsonNode node) {
        JsonNode nameNode = node.get("name");
        JsonNode descNode = node.get("desc");

        if(nameNode == null || descNode == null) {
            log.error("Missing required fields in mindscape node for {}", agentName);
            return new HoyoMindscape("", "");
        }



        return new HoyoMindscape(nameNode.asText(), descNode.asText());
    }
}
