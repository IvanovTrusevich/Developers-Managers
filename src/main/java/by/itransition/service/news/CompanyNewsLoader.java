package by.itransition.service.news;

import by.itransition.data.model.News;
import by.itransition.data.model.Project;
import by.itransition.data.repository.NewsRepository;
import by.itransition.service.news.NewsParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
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
        PageRequest pageRequest = new PageRequest(0,10);
        Slice<News> news = newsRepository.findAll(pageRequest);
        news.nextPageable();
        return newsParser.parseNews(news,locale);
    }
}
