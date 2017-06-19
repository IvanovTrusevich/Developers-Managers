package by.itransition.web;

import by.itransition.data.model.News;
import by.itransition.data.model.Photo;
import by.itransition.data.model.Project;
import by.itransition.data.model.User;
import by.itransition.data.repository.UserRepository;
import by.itransition.service.news.NewsLoader;
import by.itransition.service.photo.impl.CloudinaryService;
import by.itransition.service.user.UserService;
import com.google.common.collect.ImmutableMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.util.*;

@Controller
@RequestMapping(value = {"/"})
public class ProfileController {
    private UserRepository userRepository;

    private NewsLoader newsLoader;

    @Autowired
    public void setNewsLoader(NewsLoader newsLoader) {
        this.newsLoader = newsLoader;
    }

    @Autowired
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping(value = "/profile/{username}")
    public ModelAndView getUser(@PathVariable("username") String username, Locale locale){
        User user = userRepository.findByUsername(username);
        Map<Date,String> news = newsLoader.loadUserNews(locale, username);

        Map<Project,Integer> projects = new HashMap<>();
        for(Project project : user.getProjects()) {
            projects.put(project,project.getDevelopers().size());
        }
        String role = getUserRole(user);

        return new ModelAndView("profile",ImmutableMap.of("user", user, "news", news,
                "projects", projects, "role", role));
    }

    private String getUserRole(User user) {
        String role;
        switch (user.getAuthorities().size()) {
            case 1:
                role = "developer";
                break;
            case 2:
                role = "manager";
                break;
            case 3:
                role = "admin";
                break;
            default: role = "";
        }
        return role;
    }

}
