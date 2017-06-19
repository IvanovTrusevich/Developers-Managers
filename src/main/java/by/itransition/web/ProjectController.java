package by.itransition.web;

import by.itransition.data.model.Project;
import by.itransition.data.model.Tag;
import by.itransition.service.news.NewsLoader;
import by.itransition.service.project.ManageProjectService;
import by.itransition.service.project.ProjectService;
import by.itransition.web.exception.ResourceNotFoundException;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.*;

@Controller
@RequestMapping(value = {"/"})
public class ProjectController {

    private final ProjectService projectService;

    private final NewsLoader newsLoader;




    @Autowired
    public ProjectController(ProjectService projectService, NewsLoader newsLoader) {
        this.projectService = projectService;
        this.newsLoader = newsLoader;
    }

    @GetMapping(value = "/projects/{projectName}")
    public String getHome(Model model, @PathVariable("projectName") String projectName, Locale locale) {
        if (!projectService.exists(projectName))
            throw new ResourceNotFoundException("project not found");

        model.addAttribute("readme", Lists.newArrayList(projectService.getReadme(projectName).split("\n")));
        model.addAttribute("tags", projectService.getCurrentProjectTags(projectName));
        model.addAttribute("repoUrl", projectService.getRepoUrl(projectName));
        model.addAttribute("repoName", projectService.getRepoName(projectName));
        model.addAttribute("wiki", projectService.getWikiContent(projectName));
        model.addAttribute("news", newsLoader.loadProjectNews(locale, projectName));
        model.addAttribute("project", projectService.getProject(projectName));

        return "project";
    }

    @GetMapping(value = "/newProject")
    public ModelAndView newProject() {
        return new ModelAndView("newProject");
    }
}
