package com.ots.dpel.global.utils;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author sdimitriadis
 * Ορισμός μορφοποίησης ημερομηνιών στην εισαγωγή των ημερομηνιών από μορφή Json
 */
@Component
public class JsonDateTimeDeserializer extends JsonDeserializer<Date> {
    
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm");
    
    @Override
    public Date deserialize(JsonParser jsonParser, DeserializationContext context)
        throws IOException, JsonProcessingException {
        
        String date = jsonParser.getText();
        try {
            if (date.isEmpty()) {
                return null;
            } else {
                return dateFormat.parse(date);
            }
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        
    }
    
}
