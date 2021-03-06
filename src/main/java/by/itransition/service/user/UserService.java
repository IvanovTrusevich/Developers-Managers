package by.itransition.service.user;

import by.itransition.data.model.User;
import by.itransition.data.model.dto.UserDto;
import by.itransition.data.repository.UserRepository;
import by.itransition.service.user.exception.UserExistsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * @author Ilya Ivanov
 */
@Service("userService")
public class UserService implements RegistrationService, UserDetailsService {
    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private CredentialsPolicy credentialsPolicy;

    private PasswordGenerator passwordGenerator;

    private AuthorityPolicy authorityPolicy;

    @Autowired
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, CredentialsPolicy credentialsPolicy, AuthorityPolicy authorityPolicy) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.credentialsPolicy = credentialsPolicy;
        this.authorityPolicy = authorityPolicy;
    }

    @Override
    public User registerNewUserAccount(UserDto accountDto) throws UserExistsException, IllegalAccessException, InstantiationException {
        final String email = accountDto.getEmail();
        if (usernameExist(email)) {
            throw new UserExistsException("There is an account with that username: + accountDto.getEmail()");
        }

        String password = accountDto.getPassword();

        if(credentialsPolicy.alwaysGenerateOnRegistration()) {
            if(passwordGenerator == null) {
                Class<PasswordGenerator> passwordGeneratorType =
                        credentialsPolicy.defaultPasswordGeneratorType();
                passwordGenerator = passwordGeneratorType.newInstance();
            }
            password = passwordGenerator.generate();
        }

        String encodedPassword = passwordEncoder.encode(password);
        User user = new User(email, encodedPassword);
        final GrantedAuthority defaultAuthority = authorityPolicy.getDefaultRegistrationAuthority();
        user.addAuthority(defaultAuthority);
        return userRepository.save(user);
    }

    private boolean usernameExist(String email) {
        return userRepository.findByEmail(email) != null;
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        final User byEmail = userRepository.findByEmail(s);
        if (byEmail == null)
            throw new UsernameNotFoundException("User not found");
        return byEmail;
    }

    public void setAuthorityPolicy(AuthorityPolicy authorityPolicy) {
        this.authorityPolicy = authorityPolicy;
    }

    public void setCredentialsPolicy(CredentialsPolicy credentialsPolicy) {
        this.credentialsPolicy = credentialsPolicy;
    }

    public void setPasswordGenerator(PasswordGenerator passwordGenerator) {
        this.passwordGenerator = passwordGenerator;
    }
}
