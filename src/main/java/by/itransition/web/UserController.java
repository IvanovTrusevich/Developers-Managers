package by.itransition.web;

import by.itransition.data.model.User;
import by.itransition.data.model.dto.UserDto;
import by.itransition.service.user.UserService;
import by.itransition.service.user.exception.UserExistsException;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

/**
 * @author Ilya Ivanov
 */
@Controller
@RequestMapping(value = {"/"})
public class UserController {
    private static final Logger log = Logger.getLogger(UserController.class);

    private final UserService service;

    @Autowired
    public UserController(UserService service) {
        this.service = service;
    }

    @RequestMapping(value = "/registration", method = GET)
    public String register(Model model) {
        model.addAttribute("user", UserDto.PLACEHOLDER);
        return "registration";
    }

    @RequestMapping(value = "/registration", method = POST)
    public ModelAndView registerUserAccount(
            @ModelAttribute("user") @Valid UserDto accountDto, BindingResult result) {
        User registered = null;
        if (!result.hasErrors()) {
            registered = createUserAccount(accountDto, result);
        }
        if (result.hasErrors()) {
            return new ModelAndView("registration");
        } else {
            log.debug("New user: " + registered);
            return new ModelAndView("registration", "success", "true");
        }
    }

    private User createUserAccount(UserDto accountDto, BindingResult result) {
        try {
            return service.registerNewUserAccount(accountDto);
        } catch (UserExistsException e) {
            result.rejectValue("email", "Already exists");
            return null;
        } catch (IllegalAccessException | InstantiationException e) {
            result.reject("Registration service is currently unavailable");
            log.error("Registration service error:", e);
            return null;
        }
    }
}
