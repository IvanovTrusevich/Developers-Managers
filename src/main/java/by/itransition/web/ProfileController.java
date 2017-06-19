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

    private CloudinaryService cloudinaryService;
    private UserRepository userRepository;
    private NewsLoader newsLoader;

    @Autowired
    public void setNewsLoader(NewsLoader newsLoader) {
        this.newsLoader = newsLoader;
    }

    @Autowired
    public void setCloudinaryService(CloudinaryService cloudinaryService) {
        this.cloudinaryService = cloudinaryService;
    }

    @Autowired
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping(value = "/profile/{username}")
    public ModelAndView getUser(@PathVariable("username") String username, Locale locale){
        User user = userRepository.findByUsername(username);
        Map<Date,String> news  = newsLoader.loadUserNews(locale, username);

        Map<Project,Integer> projects = new HashMap<>();
        for(Project project : user.getProjects()) {
            projects.put(project,project.getDevelopers().size());
        }
        String role = getUserRole(user);

        return new ModelAndView("profile",ImmutableMap.of("user", user, "news", news,
                "projects", projects, "role", role));
    }

    @GetMapping(value = "/profile/{username}/edit")
    public ModelAndView getUserEditPage(String username){
        User user = userRepository.findByUsername(username);
        return new ModelAndView("userEdit","user", user);
    }

    @PostMapping(value = "/profile/changePicture/")
    @ResponseBody
    public void changePicture(byte[] bytes, long userId) throws IOException {
        Photo photo = cloudinaryService.uploadFile(bytes);
        User user = userRepository.findOne(userId);
        user.setPhoto(photo);
        userRepository.save(user);
    }

    public void changeFirstName(String newName, long userId){
        userRepository.updateFirstNameById(newName,userId);
    }

    public void changeMiddleName(String newName, long userId){
        userRepository.updateMiddleNameById(newName,userId);
    }

    public void changeLastName(String newName, long userId){
        userRepository.updateLastNameById(newName,userId);
    }

    public void changeUserName(String newName, long userId){
        userRepository.updateUserNameById(newName,userId);
    }

    private String getUserRole(User user) {
        String role = null;
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
