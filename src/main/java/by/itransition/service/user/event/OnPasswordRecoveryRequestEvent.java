package by.itransition.service.user.event;

import by.itransition.data.model.User;
import org.springframework.beans.factory.annotation.Value;

import java.util.Locale;

/**
 * Created by ilya on 6/15/17.
 */
public class OnPasswordRecoveryRequestEvent extends DeferredUserEvent {
    private static String callbackUrl = "/recovery";

    private static String subjectKey = "user.recovery.subject";

    private static String messageKey = "user.recovery.message";

    public OnPasswordRecoveryRequestEvent(User user, Locale locale, String appUrl) {
        super(user, locale, appUrl, callbackUrl, subjectKey, messageKey);
    }
}
