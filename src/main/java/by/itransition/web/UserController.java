package by.itransition.web;

import by.itransition.data.model.RecoveryToken;
import by.itransition.data.model.User;
import by.itransition.data.model.dto.UserDto;
import by.itransition.service.recovery.RecoveryService;
import by.itransition.service.user.impl.UserService;
import by.itransition.service.user.exception.AlreadyExistsException;
import com.google.common.collect.ImmutableMap;
import org.apache.log4j.Logger;
import org.elasticsearch.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.validation.Valid;
import java.io.IOException;
import java.util.Locale;

/**
 * @author Ilya Ivanov
 */
@Controller
@RequestMapping(value = {"/"})
public class UserController {
    private static final Logger log = Logger.getLogger(UserController.class);

    private final UserService userService;

    private final RecoveryService recoveryService;

    private final MessageSource messageSource;

    private final JavaMailSender javaMailSender;

    @Autowired
    public UserController(UserService userService, RecoveryService recoveryService, MessageSource messageSource, JavaMailSender javaMailSender) {
        this.userService = userService;
        this.recoveryService = recoveryService;
        this.messageSource = messageSource;
        this.javaMailSender = javaMailSender;
    }

    @GetMapping(value = "/login")
    public ModelAndView login(@RequestParam(name = "lost", defaultValue = "false") String lost,
                              @RequestParam(name = "error", defaultValue = "false") String error,
                              @RequestParam(name = "success", defaultValue = "false") String success) {
        return new ModelAndView("login",
                ImmutableMap.of("lost", lost, "hasError", error, "success", success));
    }

    @PostMapping(value = "/lost")
    @ResponseBody
    public ResponseEntity<String> lostPassword(Locale locale, @RequestParam("credentials") String credentials) {
        log.info("Credentials: " + credentials);
        final User oneWithCredentials = userService.findOneWithCredentials(credentials);
        if (oneWithCredentials == null)
            return ResponseEntity.badRequest().body(messageSource.getMessage("lost.error.exists", null, locale));
        try {
            sendPasswordRecoveryEmail(oneWithCredentials);
        } catch (MessagingException e) {
            return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(messageSource.getMessage("lost.error.email", null, locale));
        }
        return ResponseEntity.ok(messageSource.getMessage("lost.success", null, locale));
    }

    private void sendPasswordRecoveryEmail(User user) throws MessagingException {
        MimeMessage mimeMessage = createMessage(user.getEmail());
        final String passwordRecoveryLink = getPasswordRecoveryLink(user);
        mimeMessage.setSubject("Devman - password recovery");
        mimeMessage.setText("To set new password follow next link: " + passwordRecoveryLink);
        javaMailSender.send(mimeMessage);
    }

    private MimeMessage createMessage(String email) throws MessagingException {
        final MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        final InternetAddress recipient = new InternetAddress(email);
        mimeMessage.addRecipient(Message.RecipientType.TO, recipient);
        return mimeMessage;
    }

    private String getPasswordRecoveryLink(User user) {
        final RecoveryToken token = recoveryService.createRecoveryToken(user);
        return token.getToken();
    }

    @GetMapping("/recovery/{token}")
    public String lostPasswordRecoveryForm(@PathVariable("token") String token) {
        log.info("Token: " + token);
        final User user = recoveryService.getUser(token);
        if (user != null)
            return "recovery";
        else throw new ResourceNotFoundException("No recovery request found");
    }

    @GetMapping(value = "/registration")
    public ModelAndView register() {
        return new ModelAndView("registration", "userForm", UserDto.getPlaceholder());
    }

    @PostMapping(value = "/registration")
    public ModelAndView processRegistration(@Valid UserDto accountDto, BindingResult result) {
        if (!result.hasErrors()) {
            createUserAccount(accountDto, result);
        }
        if (result.hasErrors()) {
            return new ModelAndView("registration", "userForm", accountDto);
        } else {
            return new ModelAndView("registration", "success", "true");
        }
    }

    private void createUserAccount(UserDto accountDto, BindingResult result) {
        try {
            final User user = userService.registerNewUserAccount(accountDto);
            log.debug("New user: " + user);
        } catch (AlreadyExistsException e) {
            result.rejectValue("email", "Already exists");
        } catch (IllegalAccessException | InstantiationException | IOException e) {
            result.reject("Registration userService is currently unavailable");
            log.error("Registration userService error:", e);
        }
    }

    @GetMapping("/recovery/{token}")
    public String activateAccount(@PathVariable("token") String token) {
        log.info("Token: " + token);
        final User user = recoveryService.getUser(token);
        if (user != null)
            return "recovery";
        else throw new ResourceNotFoundException("No recovery request found");
    }


}
