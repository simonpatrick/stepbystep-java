package com.hedwig.stepbystep.javatutorial.jackson.serialization;

import java.io.IOException;


import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.hedwig.stepbystep.javatutorial.jackson.dtos.ItemWithSerializer;

public class ItemSerializerOnClass extends JsonSerializer<ItemWithSerializer> {

    @Override
    public final void serialize(final ItemWithSerializer value, final JsonGenerator jgen, final SerializerProvider provider) throws IOException, JsonProcessingException {
        jgen.writeStartObject();
        jgen.writeNumberField("id", value.id);
        jgen.writeStringField("itemName", value.itemName);
        jgen.writeNumberField("owner", value.owner.id);
        jgen.writeEndObject();
    }

}