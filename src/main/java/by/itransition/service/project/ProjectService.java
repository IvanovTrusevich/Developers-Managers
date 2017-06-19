package by.itransition.service.project;

import by.itransition.data.model.News;
import by.itransition.data.model.Project;
import by.itransition.data.model.Tag;
import by.itransition.data.model.User;
import by.itransition.data.repository.ProjectRepository;
import by.itransition.data.repository.TagRepository;
import by.itransition.data.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class ProjectService {

    private ProjectRepository projectRepository;
    private TagRepository tagRepository;
    private UserRepository userRepository;

    @Autowired
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Autowired
    public void setTagRepository(TagRepository tagRepository) {
        this.tagRepository = tagRepository;
    }

    @Autowired
    public void setProjectRepository(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }

    public String getReadme(String projectName) {
        return projectRepository.findGitReadmeByProjectName(projectName);
    }

    public boolean exists(String projectName) {
        return projectRepository.findByProjectName(projectName) != null;
    }

    public Set<Tag> getCurrentProjectTags(String projectName) {
        return projectRepository.findTagsByProjectName(projectName);
    }

    public String getWikiContent(String projectName) {
        return projectRepository.findWikiContentByProjectName(projectName);
    }

    public Set<News> getNews(String projectName) {
        return projectRepository.findNewsByProjectName(projectName);
    }

    public String getRepoUrl(String projectName) {
        return projectRepository.findGitRepoUrlByProjectName(projectName);
    }

    public String getRepoName(String projectName) {
        return projectRepository.findGitRepoNameByProjectName(projectName);
    }

    public void setWikiLastEditor(String projectName, Long userId){
        Project project = projectRepository.findByProjectName(projectName);
        User user  = userRepository.findOne(userId);
        project.setWikiLastEditor(user);
        projectRepository.save(project);
    }

    public String getWikiLastEditor(String projectName) {
        User user =  projectRepository.findWikiLastEditorByProjectName(projectName);
        if (user == null){
            return "";
        }
        return user.getUsername();
    }

    public void addTagToProject(String projectName, String tagName, double weight) {
        Project project = projectRepository.findByProjectName(projectName);
        if (project == null) {
            throw new RuntimeException("unable to find project " + projectName);
        }
        Set<Project> projects = tagRepository.findProjectsByTagName(tagName);
        if (projects == null || projects.size() == 0) {
            projects = new HashSet<>();
            projects.add(project);
            tagRepository.save(new Tag(tagName, weight, projects));
            return;
        }
        Tag tag = tagRepository.findByTagName(tagName);
        projects.add(project);
        tag.setProjects(projects);
        tagRepository.save(tag);
    }

    public Set<Project> getActiveProjects(){
        return projectRepository.findByEnabled(true);
    }
}
