package by.itransition.web;

import by.itransition.data.model.Gender;
import by.itransition.data.model.User;
import by.itransition.data.model.dto.AccountDto;
import by.itransition.data.model.dto.PhotoDto;
import by.itransition.data.model.dto.ProfileDto;
import by.itransition.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.io.IOException;

/**
 * Created by ilya on 6/19/17.
 */
@Controller
@RequestMapping("")
public class SettingController {
    private final UserService userService;

    @Autowired
    public SettingController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/settings")
    public String getSettings(Model model, Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        model.addAttribute("personalForm", ProfileDto.createProfileDto(user));
        model.addAttribute("accountForm", AccountDto.getPlaceholder());
        return "settings";
    }

    @PostMapping("/settings/personal")
    public ModelAndView processPersonalSettings(
            @ModelAttribute("personalForm") ProfileDto profileDto, BindingResult result, Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        final ModelAndView modelAndView = new ModelAndView("settings", "accountForm", AccountDto.getPlaceholder());
        modelAndView.addObject("user", user);
        if (result.hasErrors()) {
            updateUserPersonal(user, profileDto);
            userService.saveRegisteredUser(user);
            modelAndView.addObject("success", "true");
        }
        return modelAndView;
    }

    private void updateUserPersonal(User user, ProfileDto profileDto) {
        final String firstName = profileDto.getFirstName();
        if (firstName != null && !firstName.isEmpty())
            user.setFirstName(firstName);
        final String lastName = profileDto.getLastName();
        if (lastName != null && !lastName.isEmpty())
            user.setLastName(lastName);
        final String middleName = profileDto.getMiddleName();
        if (middleName != null && !middleName.isEmpty())
            user.setMiddleName(middleName);
        final Gender gender = profileDto.getGender();
        if (gender != null)
            user.setGender(gender);
    }

    @PostMapping("/settings/account")
    public ModelAndView processAccountSettings(
            @ModelAttribute("accountForm") AccountDto accountDto, BindingResult result, Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        final ModelAndView modelAndView = new ModelAndView("settings", "accountForm", AccountDto.getPlaceholder());
        modelAndView.addObject("user", user);
        if (result.hasErrors()) {
            userService.changeUserPassword(user, accountDto);
            modelAndView.addObject("success", "true");
        }
        return modelAndView;
    }

    @PostMapping("/settings/photo")
    @ResponseBody
    public ResponseEntity<String> processPhotoUpdate(@Valid PhotoDto photoDto, BindingResult result, Authentication authentication) throws IOException {
        if (!result.hasErrors()) {
            User user = (User) authentication.getPrincipal();
            userService.changeProfileImage(user, photoDto.getProfileImage().getBytes());
            return ResponseEntity.ok(user.getPhoto().getImage());
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }
}
