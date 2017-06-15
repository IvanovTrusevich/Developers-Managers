package by.itransition.service.user.deferred;

import by.itransition.data.model.RecoveryToken;
import by.itransition.data.model.User;
import by.itransition.service.user.RecoveryService;
import by.itransition.service.user.event.OnPasswordRecoveryRequestEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.MessageSource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.UUID;

/**
 * Created by ilya on 6/14/17.
 */
@Service
public class RecoveryRequestListener extends DeferredMailListener<OnPasswordRecoveryRequestEvent> {
    private final RecoveryService recoveryService;

    @Autowired
    public RecoveryRequestListener(RecoveryService recoveryService, MessageSource messages, JavaMailSender mailSender) {
        super(messages, mailSender);
        this.recoveryService = recoveryService;
    }

    @Override
    protected void persistToken(User user, String token) {
        recoveryService.createRecoveryToken(user, token);
    }
}
