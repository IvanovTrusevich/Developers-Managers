package by.itransition.service.news;

import by.itransition.data.model.News;
import by.itransition.data.model.NewsType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class NewsParser {
    private final MessageSource messageSource;

    @Autowired
    public NewsParser(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    public Map<Date,String> parseNews(List<News> news, Locale locale) {
        Map<Date,String> parsedNews = new HashMap<>();
        for (News pieceOfNews : news) {
            String format = messageSource.getMessage(pieceOfNews.getNewsType().getFormatStringAddress(), null, locale);
            String result;
            if (pieceOfNews.getNewsType() == NewsType.DEVELOPER_TO_PROJECT || pieceOfNews.getNewsType() == NewsType.NEW_PROJECT) {
                result = String.format(format, pieceOfNews.getUserNews().getFirstName() + " " + pieceOfNews.getUserNews().getLastName(), pieceOfNews.getProjectNews().getProjectName());
            } else if (pieceOfNews.getProjectNews() == null) {
                result = String.format(format, pieceOfNews.getUserNews().getFirstName() + " " + pieceOfNews.getUserNews().getLastName());
            } else {
                result = String.format(format, pieceOfNews.getProjectNews().getProjectName());
            }
            parsedNews.put(pieceOfNews.getDate(),result);
        }
        return parsedNews;
    }

}
