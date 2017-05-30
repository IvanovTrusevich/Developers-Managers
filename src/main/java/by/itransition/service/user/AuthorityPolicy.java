package by.itransition.service.user;

import org.springframework.security.core.GrantedAuthority;

/**
 * Created by ilya on 5/29/17.
 */
public interface AuthorityPolicy {
    GrantedAuthority getDefaultRegistrationAuthority();
}
