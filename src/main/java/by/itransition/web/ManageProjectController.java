package by.itransition.web;

import by.itransition.data.model.Project;
import by.itransition.service.Project.ManageProjectService;
import com.google.common.collect.ImmutableMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping(value = {"/projectManagement"})
public class ManageProjectController {

    private ManageProjectService manageProjectService;

    @Autowired
    public void setManageProjectService(ManageProjectService manageProjectService) {
        this.manageProjectService = manageProjectService;
    }

    @GetMapping(value = "/")
    public ModelAndView showAllProjects (){
        List<Project> projects = manageProjectService.getAllProjects();
        return new ModelAndView("project", ImmutableMap.of("wiki", projects));
    }

    @GetMapping(value = "/create")
    public void createNewProject (String projectName, String gitRepoName, long managerId, boolean isRepoCreated){
        if(!isRepoCreated){
            manageProjectService.createGitRepo(gitRepoName);
        }
        manageProjectService.createNewProject(projectName,gitRepoName,managerId);
    }

    @GetMapping(value = "/archivate/{projectName}/")
    public void archivate (String projectName){
        manageProjectService.archivateProject(projectName);
    }

    @GetMapping(value = "/{projectName}/dearchivate")
    public void dearchivate (String projectName){
        manageProjectService.dearchivateProject(projectName);
    }
}
