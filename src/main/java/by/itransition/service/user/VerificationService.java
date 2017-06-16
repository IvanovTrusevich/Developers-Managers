package by.itransition.service.user;

import by.itransition.data.model.User;
import by.itransition.data.model.VerificationToken;

import java.util.Optional;

/**
 * Created by ilya on 6/15/17.
 */
public interface VerificationService {
    void createVerificationToken(User user, String token);

    Optional<VerificationToken> getVerificationToken(String token);

    void deleteUsedVerificationToken(String token);

    void activateUser(User user, String token);
}
