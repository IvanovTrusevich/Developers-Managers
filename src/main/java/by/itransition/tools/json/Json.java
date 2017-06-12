package by.itransition.tools.json;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.Map;

public class Json {

    public String mapToString(Map<String,Object> map){
        ObjectMapper mapper = new ObjectMapper();
        String json = null;
        try {
            json = mapper.writeValueAsString(map);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return json;
    }

    public Map<String,Object> stringToMap(String json){
        ObjectMapper mapper = new ObjectMapper();
        Map<String,Object> map = null;
        try {
            map = mapper.readValue(json,new TypeReference() {
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
        return map;
    }
}
