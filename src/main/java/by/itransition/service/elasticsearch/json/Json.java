package by.itransition.service.elasticsearch.json;

import by.itransition.service.elasticsearch.model.ElasticModelInterface;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.util.List;

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
            List<ElasticModelInterface> list = mapper.readValue(json,new TypeReference() {
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
        return t;
    }

}
