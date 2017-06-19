package by.itransition.service.elasticsearch.searcher;

import okhttp3.*;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

@Component
public class SearchManager {

    private OkHttpClient okHttpClient;
    private String url = "http://localhost:9200/devman/_search";
    private OkHttpClient getHttpClient() {
        if (okHttpClient == null) {
            okHttpClient = new OkHttpClient.Builder()
                    .connectionPool(new ConnectionPool())
                    .connectTimeout(15, TimeUnit.SECONDS)
                    .writeTimeout(15, TimeUnit.SECONDS)
                    .readTimeout(15, TimeUnit.SECONDS)
                    .build();
        }
        return okHttpClient;
    }

    public String find(String searchQuery) throws IOException {

        RequestBody postData = RequestBody.create(
                MediaType.parse("application/json"), searchQuery);
        OkHttpClient client = getHttpClient();
        Request request = new Request.Builder()
                .url(url)
                .post(postData)
                .build();

        Response res = client.newCall(request).execute();
        String result = res.body().string();
        //boolean success = res.isSuccessful();

        res.body().close();
        res.close();

        return result;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
