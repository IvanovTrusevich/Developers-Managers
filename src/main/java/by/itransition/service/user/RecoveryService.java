package by.itransition.service.user;

import by.itransition.data.model.RecoveryToken;
import by.itransition.data.model.User;
import by.itransition.data.model.dto.PasswordDto;
import by.itransition.service.user.event.OnPasswordRecoveryRequestEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Created by ilya on 6/14/17.
 */
public interface RecoveryService {
    void createRecoveryToken(User user, String token);

    Optional<RecoveryToken> findByRecoveryToken(String token);

    void changeUserPassword(User user, PasswordDto passwordDto);

    void deleteUserRecoveryToken(String token);
}
