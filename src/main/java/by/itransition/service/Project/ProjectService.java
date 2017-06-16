package by.itransition.service.Project;

import by.itransition.data.repository.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
}
