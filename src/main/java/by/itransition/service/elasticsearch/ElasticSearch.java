package by.itransition.service.elasticsearch;

import by.itransition.service.elasticsearch.model.ElasticModelInterface;
import by.itransition.service.elasticsearch.json.Json;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.entity.ContentType;
import org.apache.http.nio.entity.NStringEntity;
import org.apache.http.util.EntityUtils;
import org.elasticsearch.client.Response;
import org.elasticsearch.client.RestClient;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class ElasticSearch {

    private RestClient restClient;
    private static final String index = "devman";


//    public static void main(String[] args) throws UnknownHostException {
//        ElasticSearch es = new ElasticSearch();
//        es.openClient();
//
//        Map<String, String> paramMap = new HashMap<>();
//        paramMap.put("q", "content:cats");
//        paramMap.put("pretty", "true");
//        es.findDocuments(paramMap);
//
//        //Map<String, Object> paramMap = new HashMap<>();
//        //paramMap.put("title", "Funny lizards");
//        //paramMap.put("content", "<p>Funny story about lizards<p>");
//        //List<String>  tags = new ArrayList<String>();
//        //tags.add("lizards");
//        //tags.add("funny");
//        //paramMap.put("tags", tags);
//        //es.createDocument(paramMap,"blog","post",3);
//
//        //es.deleteDocument("blog","post",3);
//    }

    public ElasticSearch() {
    }

    private void openClient() {
        if (restClient == null) {
            restClient = RestClient.builder(
                    new HttpHost("localhost", 9200, "http"),
                    new HttpHost("localhost", 9205, "http")).build();
        }
    }

    public <T extends ElasticModelInterface> void createDocument(String type, T t) {
        openClient();
        String endpoint = "/" + index + "/" + type + "/" + t.getId();
        HttpEntity entity = new NStringEntity(new Json().objectToJson(t), ContentType.APPLICATION_JSON);
        try {
            restClient.performRequest("PUT", endpoint, Collections.emptyMap(), entity);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public <T extends ElasticModelInterface> T getObject(String searchResult){

        List<T> result = new Json().jsonToObject(searchResult);
        return null;
    }
}
