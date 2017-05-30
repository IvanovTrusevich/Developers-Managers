package by.itransition.service.user;

import by.itransition.data.model.User;
import by.itransition.data.model.dto.UserDto;
import by.itransition.service.user.exception.UserExistsException;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.Optional;

/**
 * @author Ilya Ivanov
 */
public interface RegistrationService extends UserDetailsService {
    User registerNewUserAccount(UserDto accountDto) throws UserExistsException, IllegalAccessException, InstantiationException;
}
