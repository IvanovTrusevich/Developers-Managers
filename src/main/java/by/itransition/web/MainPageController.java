package by.itransition.web;

import by.itransition.data.model.Project;
import by.itransition.data.repository.UserRepository;
import by.itransition.service.project.ProjectService;
import by.itransition.service.news.NewsLoader;
import by.itransition.service.news.NewsSaver;
import com.google.common.collect.ImmutableMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.*;

@Controller
@RequestMapping(value = {"/"})
public class MainPageController {

    private ProjectService projectService;
    private NewsLoader newsLoader;
    private NewsSaver newsSaver;
    private UserRepository userRepository;

    @Autowired
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Autowired
    public void setNewsSaver(NewsSaver newsSaver) {
        this.newsSaver = newsSaver;
    }

    @Autowired
    public void setProjectService(ProjectService projectService) {
        this.projectService = projectService;
    }

    @Autowired
    public void setNewsLoader(NewsLoader newsLoader) {
        this.newsLoader = newsLoader;
    }

    @GetMapping(value = "/index")
    public ModelAndView getHome(Locale locale) {
        Set<Project> projects = projectService.getActiveProjects();
        Map<Date,String> news = newsLoader.loadLastNews(locale);
        return new ModelAndView("index", ImmutableMap.of("news", news, "projects", projects));
    }

//    @GetMapping(value = "/index")
//    public ModelAndView getHome(Locale locale) {
//
//        newsSaver.save(null,1l,"DEVELOPER_UPGRADE");
//        Set<project> projects = projectService.getActiveProjects();
//        List<String> news = companyNewsLoader.loadLastNews(locale);
//        return new ModelAndView("index", ImmutableMap.of("news", news, "projects", projects));
//    }
}
