package by.itransition.service.recovery;

import by.itransition.data.model.RecoveryToken;
import by.itransition.data.model.User;
import org.springframework.stereotype.Service;

/**
 * Created by ilya on 6/14/17.
 */
@Service
public interface RecoveryService {
    RecoveryToken createRecoveryToken(User user);

    User getUser(String token);
}
