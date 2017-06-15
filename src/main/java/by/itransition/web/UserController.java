package by.itransition.web;

import by.itransition.data.model.RecoveryToken;
import by.itransition.data.model.User;
import by.itransition.data.model.VerificationToken;
import by.itransition.data.model.dto.UserDto;
import by.itransition.service.user.RecoveryService;
import by.itransition.service.user.UserService;
import by.itransition.service.user.event.OnPasswordRecoveryRequestEvent;
import by.itransition.service.user.event.OnRegistrationCompleteEvent;
import by.itransition.service.user.exception.AlreadyExistsException;
import org.apache.log4j.Logger;
import org.elasticsearch.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * @author Ilya Ivanov
 */
@Controller
@RequestMapping(value = {"/"})
public class UserController {
    private static final Logger log = Logger.getLogger(UserController.class);

    private final UserService userService;

    private final MessageSource messageSource;

    private final ApplicationEventPublisher eventPublisher;

    @Autowired
    public UserController(UserService userService, MessageSource messageSource, ApplicationEventPublisher eventPublisher) {
        this.userService = userService;
        this.messageSource = messageSource;
        this.eventPublisher = eventPublisher;
    }

    @PostMapping(value = "/lost")
    @ResponseBody
    public ResponseEntity<String> lostPassword(Locale locale, HttpServletRequest request, @RequestParam("credentials") String credentials) {
        log.info("Credentials: " + credentials);
        final User oneWithCredentials = userService.findOneWithCredentials(credentials);
        if (oneWithCredentials == null)
            return ResponseEntity.badRequest().body(messageSource.getMessage("lost.error.exists", null, locale));
        try {
            eventPublisher.publishEvent(new OnPasswordRecoveryRequestEvent(oneWithCredentials, locale, request.getContextPath()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(messageSource.getMessage("lost.error.email", null, locale));
        }
        this.lockUser(oneWithCredentials);
        return ResponseEntity.ok(messageSource.getMessage("lost.success", null, locale));
    }

    private void lockUser(User user) {
        user.lock();
        userService.saveRegisteredUser(user);
    }

    @GetMapping("/recovery")
    public String lostPasswordRecoveryForm(@RequestParam("token") String token) {
        log.info("Token: " + token);
        final Optional<RecoveryToken> optional = userService.getUserByRecoveryToken(token);
        if (optional.isPresent()) {

            return "recovery";
        } else throw new ResourceNotFoundException("No recovery request found");
    }

    @PostMapping("/recovery")
    public String processRecovery() {
        return "";
    }

    private void unlockUser(User user) {
        user.unlock();
        userService.saveRegisteredUser(user);
    }

    @GetMapping(value = "/registration")
    public ModelAndView register() {
        return new ModelAndView("registration", "userForm", UserDto.getPlaceholder());
    }

    @PostMapping(value = "/registration")
    public ModelAndView processRegistration(@ModelAttribute("userForm") @Valid UserDto accountDto, BindingResult result, Locale locale, HttpServletRequest request) {
        Optional<User> user = Optional.empty();
        if (!result.hasErrors()) {
            user = createUserAccount(accountDto, result);
        }
        if (result.hasErrors() || !user.isPresent()) {
            return new ModelAndView("registration");
        } else {
            eventPublisher.publishEvent(new OnRegistrationCompleteEvent(user.get(), locale, request.getContextPath()));
            return new ModelAndView("registration", "success", "true");
        }
    }

    private Optional<User> createUserAccount(UserDto accountDto, BindingResult result) {
        try {
            return Optional.of(userService.registerNewUserAccount(accountDto));
        } catch (IllegalAccessException | InstantiationException | IOException e) {
            result.reject("Registration userService is currently unavailable");
            log.error("Registration userService error:", e);
            return Optional.empty();
        }
    }

    @GetMapping("/activate")
    public String activateAccount(Locale locale, @RequestParam("token") String token) {
        log.info("Token: " + token);
        final Optional<VerificationToken> optional = userService.getVerificationToken(token);
        if (optional.isPresent()) {
            final VerificationToken verificationToken = optional.get();
            User user = verificationToken.getUser();
            if (checkExpiration(verificationToken.getExpiryDate())) {
                String expiredMessage = messageSource.getMessage("user.activate.expired", null, locale);
                throw new ResourceNotFoundException(expiredMessage);
//                model.addAttribute("message", expiredMessage);
//                return "redirect:/404?lang=" + locale.getLanguage();
            }
            this.enableUser(user);
            return "redirect:/login?lang=" + locale;
        } else {
            String notFound = messageSource.getMessage("user.activate.notFound", null, locale);
            throw new ResourceNotFoundException(notFound);
        }
    }

    private void enableUser(User user) {
        user.setEnabled(true);
        userService.saveRegisteredUser(user);
    }

    private boolean checkExpiration(Date expiry) {
        Calendar cal = Calendar.getInstance();
        return (expiry.getTime() - cal.getTime().getTime()) <= 0;
    }
}
