package by.itransition.web;

import by.itransition.data.model.News;
import by.itransition.data.model.Tag;
import by.itransition.data.model.User;
import by.itransition.service.project.ProjectService;
import by.itransition.web.exception.ResourceNotFoundException;
import com.google.common.collect.ImmutableMap;
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

    private ProjectService projectService;

    @Autowired
    public void setProjectService(ProjectService projectService) {
        this.projectService = projectService;
    }

    @ModelAttribute("tags")
    public List<Tag> getTags(@PathVariable("projectName") String projectName) {
        return Lists.newArrayList();
    }

    @GetMapping(value = "/projects/{projectName}")
    public String getHome(Model model, @PathVariable("projectName") String projectName) {
        if (!projectService.exists(projectName))
            throw new ResourceNotFoundException("Project not found");
       model.addAttribute("readme", projectService.getReadme(projectName));
       model.addAttribute("tags", projectService.getCurrentProjectTags(projectName));
       model.addAttribute("repoUrl", projectService.getRepoUrl(projectName));
       model.addAttribute("repoName", projectService.getRepoName(projectName));
       model.addAttribute("wiki", projectService.getWikiContent(projectName));
       model.addAttribute("news", projectService.getNews(projectName));
       return "project";
    }

    @GetMapping(value = "/projects/{projectName}/wiki")
    public String getWiki() {
        return "project";
    }
}
