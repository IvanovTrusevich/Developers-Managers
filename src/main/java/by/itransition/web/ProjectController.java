package by.itransition.web;

import by.itransition.data.repository.ProjectRepository;
import by.itransition.service.Project.ProjectService;
import by.itransition.service.github.GithubService;
import by.itransition.service.news.CompanyNewsLoader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.IOException;
import java.util.Locale;
import java.util.Map;

@Controller
@RequestMapping(value = {"/"})
public class ProjectController {

    private GithubService githubService;

    private ProjectService projectService;

    private ProjectRepository projectRepository;

    @Autowired
    public void setGithubService(GithubService githubService) {
        this.githubService = githubService;
    }
    @Autowired
    public void setProjectRepository(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }
    @Autowired
    public void setProjectService(ProjectService projectService) {
        this.projectService = projectService;
    }

    @GetMapping(value = "/project/{projectName}")
    public String getReadme(@PathVariable("projectName") String projectName) {

       String readme = projectService.getReadme(projectName);

        return readme;
    }

    @GetMapping(value = "/project/.../wiki")
    public String getWikiContent(String projectName){

        String wikiContent = projectRepository.findWikiContentByProjectName(projectName);

        return wikiContent == null ? "" : wikiContent;
    }

    @GetMapping(value = "/")
    public Map<String,Float> getConcreteProjectTags(String projectName) {

//        String wikiContent = projectRepository.findTagsByProjectName(projectName);
//
//        return wikiContent == null ? "" : wikiContent;
        return null;
    }

}
