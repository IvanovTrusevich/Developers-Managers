package by.itransition.service.project;

import by.itransition.data.model.Project;
import by.itransition.data.model.User;
import by.itransition.data.repository.ProjectRepository;
import by.itransition.data.repository.UserRepository;
import by.itransition.service.github.GithubService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class ManageProjectService {

    private static final Logger log = Logger.getLogger(ManageProjectService.class);

    private ProjectRepository projectRepository;
    private UserRepository userRepository;
    private GithubService githubService;

    @Autowired
    public void setProjectRepository(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }

    @Autowired
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Autowired
    public void setGithubService(GithubService githubService) {
        this.githubService = githubService;
    }

    public void archivateProject (String projectName){
        Project project = projectRepository.findByProjectName(projectName);
        if (project == null) {
            throw new RuntimeException("unable to find project " + projectName);
        }
        project.setEnabled(false);
        projectRepository.save(project);
    }

    public void dearchivateProject (String projectName){
        Project project = projectRepository.findByProjectName(projectName);
        if (project == null) {
            throw new RuntimeException("unable to find project " + projectName);
        }
        project.setEnabled(true);
        projectRepository.save(project);
    }

    public boolean isProjectActive (String projectName) {
        return projectRepository.findByProjectName(projectName).getEnabled();
    }

    public List<Project> getAllProjects(){
        return projectRepository.findAll();
    }

    public void createNewProject(String projectName, String gitRepoName, long managerId){
        if(projectRepository.findByProjectName(projectName) != null){
            throw new RuntimeException("project with this name already exists");
        }
        if(projectRepository.findByGitRepoName(gitRepoName) != null){
            throw new RuntimeException("project with this git repo name already exists");
        }
        Set<User> managers = new HashSet<>();
        managers.add(userRepository.findOne(managerId));
        Project project = new Project(projectName, gitRepoName, managers, true);
        project.setWikiContent("");
        try{
            project.setGitRepoUrl(githubService.getGitUrl(gitRepoName));
            project.setGitReadme(githubService.getReadMe(gitRepoName));
            project.setGitLastSHA(githubService.getLastCommitSha(gitRepoName));
            githubService.getFiles(gitRepoName);
        } catch (IOException e){
            log.info("Git repo is empty, or not created");
            project.setGitReadme("");
        }
        projectRepository.save(project);
    }


    public boolean createGitRepo(String gitRepoName){
        if(projectRepository.findByGitRepoName(gitRepoName) != null){
            throw new RuntimeException("project with this git repo name already exists");
        }
        return githubService.createRepo(gitRepoName);
    }
}
