package by.itransition.service.elasticsearch.searcher;

import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class QueryBuilder {

    public String build(String searchText) throws IOException {
        return "\n" +
                "{\n" +
                "    \"query\" : {\n" +
                "         \"query_string\": {\n" +
                "   \"query\": \"*" + searchText + "*\",\n" +
                "   \"analyze_wildcard\": true,\n" +
                "   \"default_operator\" : \"AND\"\n" +
                "   }\n" +
                "}\n" +
                "}";
    }

}
