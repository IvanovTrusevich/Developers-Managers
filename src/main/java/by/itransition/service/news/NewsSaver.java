package by.itransition.service.news;

import by.itransition.data.model.News;
import by.itransition.data.model.NewsType;
import by.itransition.data.model.Project;
import by.itransition.data.model.User;
import by.itransition.data.repository.NewsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class NewsSaver {

    private NewsRepository newsRepository;

    @Autowired
    public void setNewsRepository(NewsRepository newsRepository) {
        this.newsRepository = newsRepository;
    }

    public void save(Project project, User user, NewsType newsType){
     News news = new News(newsType,user,project, new Date());
     newsRepository.save(news);
    }
}
