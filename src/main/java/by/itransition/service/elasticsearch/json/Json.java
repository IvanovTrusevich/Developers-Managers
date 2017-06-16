package by.itransition.service.elasticsearch.json;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;

public class Json {

    public <T> String objectToJson(T t){
        ObjectMapper mapper = new ObjectMapper();
        String json = null;
        try {
            json = mapper.writeValueAsString(t);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return json;
    }

    public <T> T jsonToObject(String json){
        ObjectMapper mapper = new ObjectMapper();
        T t = null;
        try {
            t = mapper.readValue(json,new TypeReference() {
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
        return t;
    }
}
