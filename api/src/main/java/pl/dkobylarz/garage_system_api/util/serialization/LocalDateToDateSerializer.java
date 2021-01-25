package pl.dkobylarz.garage_system_api.util.serialization;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class LocalDateToDateSerializer extends JsonSerializer<LocalDateTime> {

    @Override
    public void serialize(LocalDateTime dateTime, JsonGenerator generator, SerializerProvider serializerProvider)
            throws IOException {
        String formattedDateTime = dateTime.format(DateTimeFormatter.ofPattern("yyyy"));
        generator.writeString(formattedDateTime);
    }
}
