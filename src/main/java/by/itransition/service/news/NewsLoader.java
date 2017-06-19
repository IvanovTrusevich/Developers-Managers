package by.itransition.service.news;

import by.itransition.data.model.News;
import by.itransition.data.model.Project;
import by.itransition.data.model.User;
import by.itransition.data.repository.NewsRepository;
import by.itransition.data.repository.ProjectRepository;
import by.itransition.data.repository.UserRepository;
import by.itransition.service.news.NewsParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;

import java.awt.print.Pageable;
import java.util.*;

@Service
public class NewsLoader {

    private NewsParser newsParser;
    private NewsRepository newsRepository;
    private UserRepository userRepository;
    private ProjectRepository projectRepository;

    @Autowired
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Autowired
    public void setProjectRepository(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }

    @Autowired
    public void setNewsParser(NewsParser newsParser) {
        this.newsParser = newsParser;
    }
    @Autowired
    public void setNewsRepository(NewsRepository newsRepository) {
        this.newsRepository = newsRepository;
    }

    public Map<Date,String> loadLastNews(Locale locale){
        List<News> news = newsRepository.findAll();
        return newsParser.parseNews(news,locale);
    }

    public Map<Date,String> loadUserNews(Locale locale, String username){
        User user = userRepository.findByUsername(username);
        List<News> news = newsRepository.findByUserNews(user);
        return newsParser.parseNews(news,locale);
    }

    public Map<Date,String> loadProjectNews(Locale locale, String projectname){
        Project project = projectRepository.findByProjectName(projectname);
        List<News> news = newsRepository.findByProjectNews(project);
        return newsParser.parseNews(news,locale);
    }
}
