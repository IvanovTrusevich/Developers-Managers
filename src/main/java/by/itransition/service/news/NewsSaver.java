package by.itransition.service.news;

import by.itransition.data.model.News;
import by.itransition.data.model.NewsType;
import by.itransition.data.model.Project;
import by.itransition.data.model.User;
import by.itransition.data.repository.NewsRepository;
import by.itransition.data.repository.ProjectRepository;
import by.itransition.data.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class NewsSaver {

    private NewsRepository newsRepository;
    private ProjectRepository projectRepository;
    private UserRepository userRepository;

    @Autowired
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Autowired
    public void setProjectRepository(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }

    @Autowired
    public void setNewsRepository(NewsRepository newsRepository) {
        this.newsRepository = newsRepository;
    }

    public void save(Long projectId, Long userId, NewsType newsType) {

        User user = null;
        Project project = null;
        if(userId != null) {
            user = userRepository.findOne(userId);
        }
        if(projectId != null) {
            project = projectRepository.findOne(projectId);
        }
        News news = new News(newsType, user, project, new Date());
        newsRepository.save(news);
    }
}
