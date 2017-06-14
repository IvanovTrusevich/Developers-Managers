package by.itransition.web;

import by.itransition.data.model.User;
import by.itransition.data.model.dto.UserDto;
import by.itransition.service.user.impl.UserService;
import by.itransition.service.user.exception.AlreadyExistsException;
import com.google.common.collect.ImmutableMap;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.validation.Valid;
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

    private final JavaMailSender javaMailSender;

    @Autowired
    public UserController(UserService userService, MessageSource messageSource, JavaMailSender javaMailSender) {
        this.userService = userService;
        this.messageSource = messageSource;
        this.javaMailSender = javaMailSender;
    }

    @GetMapping(value = {"/login", "/lost"})
    public String login(Model model,
                        @ModelAttribute("lost") @RequestParam(name = "lost", defaultValue = "false") String lost,
                        @ModelAttribute("hasError") @RequestParam(name = "error", defaultValue = "false") String error,
                        @ModelAttribute("success") @RequestParam(name = "success", defaultValue = "false") String success) {
        log.info("Lost: " + lost);
        log.info("Error: " + error);
        log.info("Success: " + success);
        model.addAllAttributes(ImmutableMap.of("lost", lost, "hasError", error, "success", success));
        return "login";
    }

    @GetMapping(value = "/registration")
    public ModelAndView register() {
        return new ModelAndView("registration", "userForm", UserDto.getPlaceholder());
    }

    @PostMapping(value = "/lost")
    @ResponseBody
    public ResponseEntity<String> lostPassword(Locale locale, @RequestParam("credentials") String credentials) {
        log.info("Credentials: " + credentials);
        final User oneWithCredentials = userService.findOneWithCredentials(credentials);
        if (oneWithCredentials == null)
            return ResponseEntity.badRequest().body(messageSource.getMessage("lost.error.exists", null, locale));
        final MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        try {
            final InternetAddress recipient = new InternetAddress(oneWithCredentials.getEmail());
            mimeMessage.addRecipient(Message.RecipientType.TO, recipient);
            mimeMessage.setSubject("Devman - password recovery");
            mimeMessage.setText("To set new password follow next link: ");
        } catch (MessagingException e) {
            return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(messageSource.getMessage("lost.error.email", null, locale));
        }
        javaMailSender.send(mimeMessage);
        return ResponseEntity.ok(messageSource.getMessage("lost.success", null, locale));
    }

    @GetMapping("/lost/{token}")
    public String lostPasswordRecoveryForm(@PathVariable("token") String token) {
        return "lost";
    }

    @PostMapping(value = "/registration")
    public ModelAndView processRegistration(@Valid UserDto accountDto, BindingResult result) {
        User registered = null;
        if (!result.hasErrors()) {
            registered = createUserAccount(accountDto, result);
        }
        if (result.hasErrors()) {
            return new ModelAndView("registration", "userForm", accountDto);
        } else {
            log.debug("New user: " + registered);
            return new ModelAndView("registration", "success", "true");
        }
    }

    private User createUserAccount(UserDto accountDto, BindingResult result) {
        try {
            return userService.registerNewUserAccount(accountDto);
        } catch (AlreadyExistsException e) {
            result.rejectValue("email", "Already exists");
            return null;
        } catch (IllegalAccessException | InstantiationException e) {
            result.reject("Registration userService is currently unavailable");
            log.error("Registration userService error:", e);
            return null;
        }
    }
}
