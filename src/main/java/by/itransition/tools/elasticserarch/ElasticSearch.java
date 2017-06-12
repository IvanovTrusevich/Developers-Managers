package by.itransition.tools.elasticserarch;

import by.itransition.tools.json.Json;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.entity.ContentType;
import org.apache.http.nio.entity.NStringEntity;
import org.apache.http.util.EntityUtils;
import org.elasticsearch.client.Response;
import org.elasticsearch.client.RestClient;
import java.io.IOException;
import java.net.UnknownHostException;
import java.util.*;

public class ElasticSearch {

    private RestClient restClient;
    public static void main(String[] args) throws UnknownHostException {
        ElasticSearch es = new ElasticSearch();
        es.openClient();

        Map<String, String> paramMap = new HashMap<>();
        paramMap.put("q", "content:story");
        paramMap.put("pretty", "true");
        es.findDocuments(paramMap);

        //Map<String, Object> paramMap = new HashMap<>();
        //paramMap.put("title", "Funny lizards");
        //paramMap.put("content", "<p>Funny story about lizards<p>");
        //List<String>  tags = new ArrayList<String>();
        //tags.add("lizards");
        //tags.add("funny");
        //paramMap.put("tags", tags);
        //es.createDocument(paramMap,"blog","post",3);

        //es.deleteDocument("blog","post",3);
    }
    private void openClient(){
        restClient = RestClient.builder(
                new HttpHost("localhost", 9200, "http"),
                new HttpHost("localhost", 9205, "http")).build();
        System.out.println("Rest client " + restClient.toString());
    }
    public void findDocuments(Map<String, String> objects) {
        Response response = null;
        try {
            response = restClient.performRequest("GET", "/blog/_search",
                    objects);
            System.out.println(EntityUtils.toString(response.getEntity()));
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Host -" + (response != null ? response.getHost() : null));
        System.out.println("RequestLine -" + (response != null ? response.getRequestLine() : null));
    }
    public void createDocument(Map<String, Object> objects, String index, String type, long id){
        String endpoint = "/" + index + "/" + type + "/" + id +"?pretty";
        //HttpEntity entity = new NStringEntity(makeEntity(objects),ContentType.APPLICATION_JSON);
        HttpEntity entity = new NStringEntity(new Json().mapToString(objects),ContentType.APPLICATION_JSON);
        Response response = null;
        try {
            response = restClient.performRequest(
                    "PUT",
                    endpoint,
                    Collections.<String, String>emptyMap(),
                    entity
            );
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void deleteDocument( String index, String type, long id){
        String endpoint = "/" + index + "/" + type + "/" + id +"?pretty";
        Response response = null;
        try {
            response = restClient.performRequest(
                    "DELETE",
                    endpoint,
                    Collections.<String, String>emptyMap()
            );
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
