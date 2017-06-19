package by.itransition.service.user;

import by.itransition.data.model.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * Created by ilya on 6/15/17.
 */
public interface UserService extends RegistrationService, VerificationService, RecoveryService, UserDetailsService {
    User findOneWithCredentials(String credentials);

    User saveRegisteredUser(User user);

    User changeProfileImage(User user, byte[] bytes) throws IOException;
}
