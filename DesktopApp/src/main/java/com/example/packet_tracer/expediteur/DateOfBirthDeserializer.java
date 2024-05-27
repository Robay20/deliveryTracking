package com.example.packet_tracer.expediteur;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

import java.io.IOException;
import java.time.LocalDate;

public class DateOfBirthDeserializer extends StdDeserializer<LocalDate> {

    public DateOfBirthDeserializer() {
        this(null);
    }

    public DateOfBirthDeserializer(Class<?> vc) {
        super(vc);
    }

    @Override
    public LocalDate deserialize(JsonParser parser, DeserializationContext deserializationContext) throws IOException {
        JsonNode node = parser.getCodec().readTree(parser);
        if (node.isNull()) {
            // Handle case where dateOfBirth is null
            return null;
        } else if (node.isArray()) {
            // Handle case where dateOfBirth is an array
            return handleArrayCase(node);
        } else {
            // Handle case where dateOfBirth is a string
            return LocalDate.parse(node.asText()); // Assuming the string is in ISO date format (yyyy-MM-dd)
        }
    }

    // Custom method to handle array case
    private LocalDate handleArrayCase(JsonNode arrayNode) {
        int year = arrayNode.get(0).asInt();
        int month = arrayNode.get(1).asInt();
        int day = arrayNode.get(2).asInt();
        return LocalDate.of(year, month, day);
    }
}