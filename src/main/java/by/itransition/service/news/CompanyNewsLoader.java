package by.itransition.service.news;

import by.itransition.data.model.News;
import by.itransition.data.model.Project;
import by.itransition.data.repository.NewsRepository;
import by.itransition.service.news.NewsParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.awt.print.Pageable;
import java.util.List;
import java.util.Locale;

@Service
public class CompanyNewsLoader {

    private NewsParser newsParser;

    private NewsRepository newsRepository;

    @Autowired
    public void setNewsParser(NewsParser newsParser) {
        this.newsParser = newsParser;
    }
    @Autowired
    public void setNewsRepository(NewsRepository newsRepository) {
        this.newsRepository = newsRepository;
    }

    public List<String> loadLastNews(Locale locale){
        Pageable pageable = (Pageable) new PageRequest(0,10);
        List<News> news = newsRepository.find(pageable);
        return newsParser.parseNews(news,locale);
    }
}
