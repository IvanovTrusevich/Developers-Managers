package by.itransition.service.user.impl;

import by.itransition.data.model.User;
import by.itransition.data.model.dto.UserDto;
import by.itransition.data.repository.UserRepository;
import by.itransition.service.user.AuthorityPolicy;
import by.itransition.service.user.CredentialsPolicy;
import by.itransition.service.user.PasswordGenerator;
import by.itransition.service.user.RegistrationService;
import by.itransition.service.user.exception.AlreadyExistsException;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

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
    public User registerNewUserAccount(UserDto accountDto) throws AlreadyExistsException, IllegalAccessException, InstantiationException {
        final String email = accountDto.getEmail();
        if (usernameExist(email)) {
            throw new AlreadyExistsException("There is an account with that username: + accountDto.getCredentials()");
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
        final User byEmailOrUsername = userRepository.findByEmailOrUsername(s, s);
        if (byEmailOrUsername == null)
            throw new UsernameNotFoundException("User not found");
        return byEmailOrUsername;
    }

    public void setCredentialsPolicy(CredentialsPolicy credentialsPolicy) {
        this.credentialsPolicy = credentialsPolicy;
    }

    public void setPasswordGenerator(PasswordGenerator passwordGenerator) {
        this.passwordGenerator = passwordGenerator;
    }

    public void setAuthorityPolicy(AuthorityPolicy authorityPolicy) {
        this.authorityPolicy = authorityPolicy;
    }

    public User findOneWithCredentials(String credentials) {
        return userRepository.findByEmailOrUsername(credentials, credentials);
    }
}
