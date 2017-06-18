package by.itransition.web;

import by.itransition.data.model.Project;
import by.itransition.data.repository.ProjectRepository;
import by.itransition.data.repository.TagRepository;
import by.itransition.service.elasticsearch.searcher.QueryBuilder;
import by.itransition.service.elasticsearch.searcher.ResultParser;
import by.itransition.service.elasticsearch.searcher.SearchManager;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.util.Map;
import java.util.Set;

@Controller
@RequestMapping(value = {"/"})
public class SearchController {

    private SearchManager searchManager;
    private QueryBuilder queryBuilder;
    private TagRepository tagRepository;

    @Autowired
    public void setSearchManager(SearchManager searchManager) {
        this.searchManager = searchManager;
    }

    @Autowired
    public void setQueryBuilder(QueryBuilder queryBuilder) {
        this.queryBuilder = queryBuilder;
    }

    @Autowired
    public void setTagRepository(TagRepository tagRepository) {
        this.tagRepository = tagRepository;
    }

    @GetMapping(value = "/serch/{searchText}")
    public ModelAndView doSearch(String searchText) throws IOException, JSONException {

        String jsonRequest = queryBuilder.build(searchText);
        String searchResult = searchManager.find(jsonRequest);
        System.out.println(searchResult);
        Map<String,String> result = new ResultParser().parse(searchResult);
        return new ModelAndView("searchResult", "search", result);
    }

    @GetMapping(value = "/")
    public ModelAndView findProjectByTag(String tag) {
        Set<Project> projects = tagRepository.findProjectsByTagName(tag);

        return new ModelAndView("searchResult", "projects", projects);
    }
}
