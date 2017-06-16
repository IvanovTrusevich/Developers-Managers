package by.itransition.service.elasticsearch;


import by.itransition.data.model.GitFile;
import by.itransition.data.model.Project;
import by.itransition.data.model.User;
import by.itransition.data.repository.GitFileRepository;
import by.itransition.data.repository.ProjectRepository;
import by.itransition.data.repository.UserRepository;
import by.itransition.service.elasticsearch.model.ElasticProject;
import by.itransition.service.elasticsearch.model.ElasticUser;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SynchronizationService {

    private static final Logger log = Logger.getLogger(SynchronizationService.class);

    private UserRepository userRepository;
    private ProjectRepository projectRepository;
    private GitFileRepository gitFileRepository;
    private ElasticSearch elasticSearch;

    @Autowired
    public SynchronizationService(UserRepository userRepository,
                                  ProjectRepository projectRepository,
                                  GitFileRepository gitFileRepository, ElasticSearch elasticSearch) {
        this.userRepository = userRepository;
        this.projectRepository = projectRepository;
        this.gitFileRepository = gitFileRepository;
        this.elasticSearch = elasticSearch;
    }

    public void synchronizeWithSql(){
        try {
            synchronizeUsers();
            synchronizeProjects();
        } catch (Exception e){
            log.warn("Synchronization failed. If this message repeats, look at gitFilesSynchronization");
        }
    }

    private void synchronizeUsers() {
        String typeName = "user";
        List<ElasticUser>  elasticUsers = parseUsers(userRepository.findAll());
        for(ElasticUser elasticUser : elasticUsers)
            elasticSearch.createDocument(typeName, elasticUser);
    }

    private List<ElasticUser> parseUsers(List<User> users) {
        List<ElasticUser> elasticUsers = new ArrayList<>();
        for(User user : users){
            elasticUsers.add(new ElasticUser(user));
        }
        return elasticUsers;
    }

    private void synchronizeProjects() {
        String typeName = "project";
        List<ElasticProject>  elasticProjects = parseProjects(projectRepository.findAll());
        for(ElasticProject elasticProject : elasticProjects )
            elasticSearch.createDocument(typeName, elasticProject);
    }

    private List<ElasticProject> parseProjects(List<Project> projects) {
        List<ElasticProject> elasticProjects = new ArrayList<>();
        for(Project project : projects){
            elasticProjects.add(new ElasticProject(project));
        }
        return elasticProjects;
    }

}
