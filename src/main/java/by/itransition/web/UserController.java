package by.itransition.web;

import by.itransition.data.model.User;
import by.itransition.data.model.dto.UserDto;
import by.itransition.service.user.impl.UserService;
import by.itransition.service.user.exception.AlreadyExistsException;
import lombok.extern.log4j.Log4j;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.web.authentication.RememberMeServices;
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

    /**
     * Spring Security center object "authentication manager"
     */
    final AuthenticationManager authenticationManager;


    private final UserService service;

    @Autowired
    public UserController(UserService service, @Qualifier("authenticationManager") AuthenticationManager authenticationManager) {
        this.service = service;
        this.authenticationManager = authenticationManager;
    }

    @RequestMapping(value = "/registration", method = GET)
    public String register(Model model) {
        model.addAttribute("user", UserDto.getPlaceholder());
        return "registration";
    }

    @RequestMapping(value = "/lost", method = POST)
    public String lostPassword() {
        return "";
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
        } catch (AlreadyExistsException e) {
            result.rejectValue("email", "Already exists");
            return null;
        } catch (IllegalAccessException | InstantiationException e) {
            result.reject("Registration service is currently unavailable");
            log.error("Registration service error:", e);
            return null;
        }
    }
}
