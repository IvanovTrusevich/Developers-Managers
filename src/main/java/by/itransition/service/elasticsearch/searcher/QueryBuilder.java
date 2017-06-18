package by.itransition.service.elasticsearch.searcher;

import java.io.IOException;

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
