package com.hedwig.stepbystep.javatutorial.jackson.deserialization;

import java.io.IOException;


import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.IntNode;
import com.hedwig.stepbystep.javatutorial.jackson.dtos.ItemWithSerializer;
import com.hedwig.stepbystep.javatutorial.jackson.dtos.User;

public class ItemDeserializerOnClass extends JsonDeserializer<ItemWithSerializer> {

    /**
     * {"id":1,"itemNr":"theItem","owner":2}
     */
    @Override
    public ItemWithSerializer deserialize(final JsonParser jp, final DeserializationContext ctxt) throws IOException, JsonProcessingException {
        final JsonNode node = jp.getCodec().readTree(jp);
        final int id = (Integer) ((IntNode) node.get("id")).numberValue();
        final String itemName = node.get("itemName").asText();
        final int userId = (Integer) ((IntNode) node.get("owner")).numberValue();

        return new ItemWithSerializer(id, itemName, new User(userId, null));
    }

}