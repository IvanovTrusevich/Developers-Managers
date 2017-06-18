package by.itransition.web;

import by.itransition.service.elasticsearch.searcher.QueryBuilder;
import by.itransition.service.elasticsearch.searcher.ResultParser;
import by.itransition.service.elasticsearch.searcher.SearchManager;
import org.json.JSONException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.util.Map;

@Controller
public class SearchController {

    private SearchManager searchManager = new SearchManager();
    private QueryBuilder queryBuilder = new QueryBuilder();

    @GetMapping(value = "/")
    public ModelAndView doSearch(String searchText) throws IOException, JSONException {

        String jsonRequest = queryBuilder.build(searchText);
        String searchResult = searchManager.find(jsonRequest);
        System.out.println(searchResult);
        Map<String,String> result = new ResultParser().parse(searchResult);
        return new ModelAndView("", "search", result);
    }

}

