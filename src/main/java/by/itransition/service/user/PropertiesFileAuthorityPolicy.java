package by.itransition.service.user;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

/**
 * Created by ilya on 5/29/17.
 */
@Component
public class PropertiesFileAuthorityPolicy implements AuthorityPolicy {
    private GrantedAuthority defaultAuthority;

    public PropertiesFileAuthorityPolicy(
            @Value("${by.itransition.service.user.defaultAuthority}") String defaultAuthority) {
        this.defaultAuthority = new SimpleGrantedAuthority(defaultAuthority);
    }

    @Override
    public GrantedAuthority getDefaultRegistrationAuthority() {
        return defaultAuthority;
    }
}
