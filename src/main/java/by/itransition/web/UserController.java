package by.itransition.web;

import by.itransition.data.model.RecoveryToken;
import by.itransition.data.model.User;
import by.itransition.data.model.VerificationToken;
import by.itransition.data.model.dto.PasswordDto;
import by.itransition.data.model.dto.UserDto;
import by.itransition.service.user.UserService;
import by.itransition.service.user.event.OnPasswordRecoveryRequestEvent;
import by.itransition.service.user.event.OnRegistrationCompleteEvent;
import by.itransition.web.exception.ResourceNotFoundException;
import com.google.common.collect.ImmutableMap;
import org.apache.log4j.Logger;
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
import javax.transaction.Transactional;
import javax.validation.Valid;
import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Optional;

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
    public ModelAndView lostPasswordRecoveryForm(@RequestParam("token") String token) {
        final Optional<RecoveryToken> optional = userService.findByRecoveryToken(token);
        if (optional.isPresent()) {
            return new ModelAndView("recovery", ImmutableMap.of("recoveryForm", PasswordDto.getPlaceholder(), "token", token));
        } else throw new ResourceNotFoundException("No recovery request found");
    }

    @PostMapping("/recovery")
    @Transactional
    public String processRecovery(Locale locale, @ModelAttribute("recoveryForm") @Valid PasswordDto passwordRecovery, @RequestParam("token") String token, BindingResult result) {
        if (result.hasErrors()) return "recovery";
        else {
            final Optional<RecoveryToken> optional = userService.findByRecoveryToken(token);
            if (optional.isPresent()) {
                unlockUser(optional.get().getUser(), passwordRecovery, token);
                return "redirect:/login?lang=" + locale;
            }
            else throw new ResourceNotFoundException("No recovery request found");
        }
    }

    private void unlockUser(User user, PasswordDto passwordDto, String token) {
        userService.changeUserPassword(user, passwordDto);
        userService.deleteUserRecoveryToken(token);
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
    @Transactional
    public String activateAccount(Locale locale, @RequestParam("token") String token) {
        final Optional<VerificationToken> optional = userService.getVerificationToken(token);
        if (optional.isPresent()) {
            final VerificationToken verificationToken = optional.get();
            User user = verificationToken.getUser();
            if (checkExpiration(verificationToken.getExpiryDate())) {
                String expiredMessage = messageSource.getMessage("user.activate.expired", null, locale);
                throw new ResourceNotFoundException(expiredMessage);
            }
            userService.activateUser(user, token);
            return "redirect:/login?lang=" + locale;
        } else {
            String notFound = messageSource.getMessage("user.activate.notFound", null, locale);
            throw new ResourceNotFoundException(notFound);
        }
    }

    private boolean checkExpiration(Date expiry) {
        Calendar cal = Calendar.getInstance();
        return (expiry.getTime() - cal.getTime().getTime()) <= 0;
    }
}
