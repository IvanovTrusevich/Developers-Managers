package by.itransition.service.user.deferred;

import by.itransition.data.model.User;
import by.itransition.service.user.event.DeferredUserEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.MessageSource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import java.util.Locale;
import java.util.UUID;

/**
 * Created by ilya on 6/15/17.
 */
public abstract class DeferredMailListener <T extends DeferredUserEvent> implements ApplicationListener<T> {
    private final MessageSource messages;

    private final JavaMailSender mailSender;

    DeferredMailListener(MessageSource messages, JavaMailSender mailSender) {
        this.messages = messages;
        this.mailSender = mailSender;
    }

    @Override
    public void onApplicationEvent(T event) {
        final User user = event.getUser();
        final String token = UUID.randomUUID().toString();
        persistToken(user, token);
        sendPasswordRecoveryEmail(user, token, event.getLocale(), event.getAppUrl(), event.getSubjectKey(), event.getMessageKey(), event.getCallbackUrl());
    }

    private void sendPasswordRecoveryEmail(User user, String token, Locale locale, String appUrl, String subjectKey, String messageKey, String callbackUrl)  {
        SimpleMailMessage mailMessage = createMessage(user.getEmail());
        String subject = messages.getMessage(subjectKey, null, locale);
        String message = messages.getMessage(messageKey, null, locale);
        mailMessage.setSubject("Devman - " + subject);
        mailMessage.setText(message + "http://localhost:8080" + appUrl + callbackUrl + "?token=" + token);
        mailSender.send(mailMessage);
    }

    private SimpleMailMessage createMessage(String email) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(email);
        return mailMessage;
    }

    abstract protected void persistToken(User user, String token);
}
