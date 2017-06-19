package by.itransition.web;

import by.itransition.data.model.Photo;
import by.itransition.data.model.User;
import by.itransition.data.repository.UserRepository;
import by.itransition.service.photo.impl.CloudinaryService;
import com.google.common.collect.ImmutableMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;

@Controller
@RequestMapping(value = {"/"})
public class UserPageController {

    private CloudinaryService cloudinaryService;

    private UserRepository userRepository;

    @Autowired
    public void setCloudinaryService(CloudinaryService cloudinaryService) {
        this.cloudinaryService = cloudinaryService;
    }

    @Autowired
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping(value = "/users/{username}")
    public ModelAndView getUser(String username){
        User user = userRepository.findByUsername(username);
        return new ModelAndView("user","user", user);
    }

    @GetMapping(value = "/users/{username}/edit")
    public ModelAndView getUserEditPage(String username){
        User user = userRepository.findByUsername(username);
        return new ModelAndView("userEdit","user", user);
    }

    @PostMapping(value = "/users/changePicture/")
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
}
