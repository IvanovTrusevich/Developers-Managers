package by.itransition.service.user.event;

import by.itransition.data.model.User;

import java.util.Locale;

/**
 * Created by ilya on 6/15/17.
 */
public class OnRegistrationCompleteEvent extends DeferredUserEvent {
    private static String callbackUrl = "/activate";

    private static String subjectKey = "user.activate.subject";

    private static String messageKey = "user.activate.message";

    public OnRegistrationCompleteEvent(User user, Locale locale, String appUrl) {
        super(user, locale, appUrl, callbackUrl, subjectKey, messageKey);
    }
}
