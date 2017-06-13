package by.itransition.service.user;

import by.itransition.data.model.User;
import by.itransition.data.model.dto.UserDto;
import by.itransition.service.user.exception.AlreadyExistsException;
import org.springframework.security.core.userdetails.UserDetailsService;

/**
 * @author Ilya Ivanov
 */
public interface RegistrationService extends UserDetailsService {
    User registerNewUserAccount(UserDto accountDto) throws AlreadyExistsException, IllegalAccessException, InstantiationException;
}
