package me.darkkir3.proxyoutpost.utils;

import tools.jackson.core.JacksonException;
import tools.jackson.core.JsonGenerator;
import tools.jackson.databind.SerializationContext;
import tools.jackson.databind.ValueSerializer;

import java.util.Map;

public class MapToArraySerializer extends ValueSerializer<Map<?, ?>> {

    @Override
    public void serialize(Map<?, ?> map, JsonGenerator gen, SerializationContext ctxt) throws JacksonException {
        gen.writeStartArray();

        map.forEach((key, value) -> {
            gen.writeStartObject();
            gen.writePOJOProperty("key", key);
            gen.writePOJOProperty("value", value);
            gen.writeEndObject();
        });

        gen.writeEndArray();
    }
}