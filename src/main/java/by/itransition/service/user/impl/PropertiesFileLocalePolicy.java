package by.itransition.service.user.impl;

import by.itransition.service.user.LocalePolicy;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * Created by ilya on 6/18/17.
 */
@Component
public class PropertiesFileLocalePolicy implements LocalePolicy {
    @Value("${by.itransition.service.user.defaultUserLocale}")
    private String defaultUserLocale;

    @Override
    public String getDefaultUserLocale() {
        return defaultUserLocale;
    }
}
