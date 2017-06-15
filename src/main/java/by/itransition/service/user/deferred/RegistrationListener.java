package by.itransition.service.user.deferred;

import by.itransition.data.model.User;
import by.itransition.service.user.VerificationService;
import by.itransition.service.user.event.OnRegistrationCompleteEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;


/**
 * Created by ilya on 6/15/17.
 */
@Service
public class RegistrationListener extends DeferredMailListener<OnRegistrationCompleteEvent> {
    private final VerificationService verificationService;

    @Autowired
    public RegistrationListener(VerificationService verificationService, MessageSource messages, JavaMailSender mailSender) {
        super(messages, mailSender);
        this.verificationService = verificationService;
    }

    @Override
    protected void persistToken(User user, String token) {
        verificationService.createVerificationToken(user, token);
    }
}
