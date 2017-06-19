package by.itransition.service.user.impl;

import by.itransition.service.user.PasswordGenerator;
import by.itransition.service.user.SecurityPolicy;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

/**
 * Created by ilya on 6/18/17.
 */
@Component
public class PropertiesFileSecurityPolicy implements SecurityPolicy {
    @Value("${by.itransition.service.user.credential.alwaysGenerateOnRegistration}")
    private boolean alwaysGenerateOnRegistration;

    @Value("${by.itransition.service.user.credential.defaultPasswordGeneratorName}")
    private Class<PasswordGenerator> passwordGeneratorType;

    private GrantedAuthority defaultRegistrationAuthority;

    public PropertiesFileSecurityPolicy(
            @Value("${by.itransition.service.user.defaultAuthority}") String defaultRegistrationAuthority) {
        this.defaultRegistrationAuthority = new SimpleGrantedAuthority(defaultRegistrationAuthority);
    }

    @Override
    public boolean alwaysGenerateOnRegistration() {
        return alwaysGenerateOnRegistration;
    }

    @Override
    public Class<PasswordGenerator> defaultPasswordGeneratorType() {
        return passwordGeneratorType;
    }

    @Override
    public GrantedAuthority getDefaultRegistrationAuthority() {
        return defaultRegistrationAuthority;
    }
}
