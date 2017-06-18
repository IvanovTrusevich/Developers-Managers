package by.itransition.service.user.impl;

import by.itransition.service.user.PersonalizationPolicy;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * Created by ilya on 6/18/17.
 */
@Component
public class PropertiesFilePersonalizationPolicy implements PersonalizationPolicy {
    @Value("${by.itransition.service.user.defaultUserLocale}")
    private String defaultUserLocale;

    @Value("${by.itransition.service.user.defaultUserTheme}")
    private String defaultUserTheme;

    @Override
    public String getDefaultUserLocale() {
        return defaultUserLocale;
    }

    @Override
    public String getDefaultUserTheme() {
        return defaultUserTheme;
    }
}
