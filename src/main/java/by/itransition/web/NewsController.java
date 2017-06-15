package by.itransition.web;

import by.itransition.data.model.NewsType;
import by.itransition.data.model.Project;
import by.itransition.data.model.User;
import by.itransition.service.news.CompanyNewsLoader;
import by.itransition.service.news.NewsSaver;
import by.itransition.service.news.ProjectNewsLoader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Locale;

@Controller
@RequestMapping(value = {"/"})
public class NewsController {

    private CompanyNewsLoader companyNewsLoader;
    private NewsSaver newsSaver;
    private ProjectNewsLoader projectNewsLoader;
    @Autowired
    public void setCompanyNewsLoader(CompanyNewsLoader companyNewsLoader) {
        this.companyNewsLoader = companyNewsLoader;
    }

    @Autowired
    public void setNewsSaver(NewsSaver newsSaver) {
        this.newsSaver = newsSaver;
    }

    @Autowired
    public void setProjectNewsLoader(ProjectNewsLoader projectNewsLoader) {
        this.projectNewsLoader = projectNewsLoader;
    }

    @GetMapping(value = "/index")
    public String loadMainPageNews(Locale locale){
        companyNewsLoader.loadLastNews(locale);
     return null;
    }

    @GetMapping(value = "/project/.../home")
    public String loadProjectNews(Project project, Locale locale){
        projectNewsLoader.loadLastNews(project,locale);
        return null;
    }

    @GetMapping(value = "/project/любое создание новости")
    // если новость чисто пользовательская или проктная передавай null в ненужное поле
    public String createNews(Project project, User user, NewsType newsType){
        newsSaver.save(project,user,newsType);
        return null;
    }
}
