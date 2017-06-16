package by.itransition.service.Project;

import by.itransition.data.model.News;
import by.itransition.data.model.Project;
import by.itransition.data.model.Tag;
import by.itransition.data.model.User;
import by.itransition.data.repository.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class ProjectService {

    ProjectRepository projectRepository;

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

    public User getWikiLastEditor(String projectName) {
        return  projectRepository.findWikiLastEditorByProjectName(projectName);
    }
}
