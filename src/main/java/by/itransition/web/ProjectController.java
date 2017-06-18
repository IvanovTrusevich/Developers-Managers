package by.itransition.web;

import by.itransition.data.model.News;
import by.itransition.data.model.Tag;
import by.itransition.data.model.User;
import by.itransition.service.Project.ProjectService;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import java.util.*;

@Controller
@RequestMapping(value = {"/project"})
public class ProjectController {

    private ProjectService projectService;

    @Autowired
    public void setProjectService(ProjectService projectService) {
        this.projectService = projectService;
    }

    @GetMapping(value = "/{projectName}")
    public ModelAndView getHome(@PathVariable("projectName") String projectName) {
        if (!projectService.exists(projectName))
            throw new RuntimeException("project not found");
       List<String> readme = Lists.newArrayList(projectService.getReadme(projectName).split("\n"));
       Set<Tag> tags = projectService.getCurrentProjectTags(projectName);
       String repoUrl = projectService.getRepoUrl(projectName);
       String repoName = projectService.getRepoName(projectName);
       return new ModelAndView("project", ImmutableMap.of("readme", readme, "tags", tags,
               "repoUrl", repoUrl,"repoName",repoName));
    }

    @GetMapping(value = "/{projectName}/wiki")
    public ModelAndView getWiki(@PathVariable("projectName") String projectName){
        if (!projectService.exists(projectName))
            throw new RuntimeException("project not found");
        String wiki = projectService.getWikiContent(projectName);
        Set<Tag> tags = projectService.getCurrentProjectTags(projectName);
        User wikiLastEditor = projectService.getWikiLastEditor(projectName);
        return new ModelAndView("project", ImmutableMap.of("wiki", wiki, "tags", tags));
    }

    @GetMapping(value = "/{projectName}/news")
    public ModelAndView getNewsContent(@PathVariable("projectName") String projectName){
        if (!projectService.exists(projectName))
            throw new RuntimeException("project not found");
        Set<News> news = projectService.getNews(projectName);
        Set<Tag> tags = projectService.getCurrentProjectTags(projectName);
        return new ModelAndView("project", ImmutableMap.of("news", news, "tags", tags));
    }
}
