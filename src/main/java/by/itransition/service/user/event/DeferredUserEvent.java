package by.itransition.service.user.event;

import by.itransition.data.model.User;
import org.springframework.context.ApplicationEvent;

import java.util.Locale;

/**
 * Created by ilya on 6/15/17.
 */
public class DeferredUserEvent extends ApplicationEvent {
    private String appUrl;
    private Locale locale;
    private User user;
    private String callbackUrl;
    private String subjectKey;
    private String messageKey;

    public DeferredUserEvent(User user, Locale locale, String appUrl, String callbackUrl, String subjectKey, String messageKey) {
        super(user);
        this.user = user;
        this.locale = locale;
        this.appUrl = appUrl;
        this.callbackUrl = callbackUrl;
        this.subjectKey = subjectKey;
        this.messageKey = messageKey;
    }

    public String getAppUrl() {
        return appUrl;
    }

    public void setAppUrl(String appUrl) {
        this.appUrl = appUrl;
    }

    public Locale getLocale() {
        return locale;
    }

    public void setLocale(Locale locale) {
        this.locale = locale;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getCallbackUrl() {
        return callbackUrl;
    }

    public void setCallbackUrl(String callbackUrl) {
        this.callbackUrl = callbackUrl;
    }

    public String getSubjectKey() {
        return subjectKey;
    }

    public void setSubjectKey(String subjectKey) {
        this.subjectKey = subjectKey;
    }

    public String getMessageKey() {
        return messageKey;
    }

    public void setMessageKey(String messageKey) {
        this.messageKey = messageKey;
    }
}
