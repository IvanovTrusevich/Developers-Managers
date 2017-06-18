package by.itransition.service.user.impl;

import by.itransition.data.model.Photo;
import by.itransition.data.model.RecoveryToken;
import by.itransition.data.model.User;
import by.itransition.data.model.VerificationToken;
import by.itransition.data.model.dto.PasswordDto;
import by.itransition.data.model.dto.UserDto;
import by.itransition.data.repository.RecoveryTokenRepository;
import by.itransition.data.repository.UserRepository;
import by.itransition.data.repository.VerificationTokenRepository;
import by.itransition.service.photo.PhotoService;
import by.itransition.service.user.*;
import by.itransition.service.user.exception.AlreadyExistsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Optional;

/**
 * @author Ilya Ivanov
 */
@Service("userService")
public class DefaultUserService implements UserService {
    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final PhotoService photoService;

    private final RecoveryTokenRepository recoveryTokenRepository;

    private final VerificationTokenRepository verificationTokenRepository;

    private CredentialsPolicy credentialsPolicy;

    private PasswordGenerator passwordGenerator;

    private AuthorityPolicy authorityPolicy;

    private LocalePolicy localePolicy;

    @Autowired
    public DefaultUserService(UserRepository userRepository, PasswordEncoder passwordEncoder, PhotoService photoService, RecoveryTokenRepository recoveryTokenRepository, VerificationTokenRepository verificationTokenRepository, CredentialsPolicy credentialsPolicy, AuthorityPolicy authorityPolicy, LocalePolicy localePolicy) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.photoService = photoService;
        this.recoveryTokenRepository = recoveryTokenRepository;
        this.verificationTokenRepository = verificationTokenRepository;
        this.credentialsPolicy = credentialsPolicy;
        this.authorityPolicy = authorityPolicy;
        this.localePolicy = localePolicy;
    }

    @Override
    public User registerNewUserAccount(UserDto accountDto) throws AlreadyExistsException, IllegalAccessException, InstantiationException, IOException {
        if (usernameExist(accountDto.getEmail(), accountDto.getUsername())) {
            throw new AlreadyExistsException("There is an account with that username: + accountDto.getCredentials()");
        }
        String password = accountDto.getPassword();
        if(credentialsPolicy.alwaysGenerateOnRegistration()) {
            if (passwordGenerator == null) {
                Class<PasswordGenerator> passwordGeneratorType = credentialsPolicy.defaultPasswordGeneratorType();
                passwordGenerator = passwordGeneratorType.newInstance();
            }
            password = passwordGenerator.generate();
        }

        String encodedPassword = passwordEncoder.encode(password);
        final Photo photo = photoService.uploadFile(accountDto);
        User user = User.createUser(accountDto, encodedPassword, photo);
        user.setLocale(localePolicy.getDefaultUserLocale());
        final GrantedAuthority defaultAuthority = authorityPolicy.getDefaultRegistrationAuthority();
        user.addAuthority(defaultAuthority);
        return userRepository.save(user);
    }

    private boolean usernameExist(String email, String username) {
        return userRepository.findByEmailOrUsername(email, username) != null;
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        final User byEmailOrUsername = userRepository.findByEmailOrUsername(s, s);
        if (byEmailOrUsername == null)
            throw new UsernameNotFoundException("User not found");
        return byEmailOrUsername;
    }

    @Override
    public User findOneWithCredentials(String credentials) {
        return userRepository.findByEmailOrUsername(credentials, credentials);
    }

    @Override
    public User saveRegisteredUser(User user) {
        return userRepository.save(user);
    }

    @Override
    public void createVerificationToken(User user, String token) {
        verificationTokenRepository.save(new VerificationToken(user, token));
    }

    @Override
    public Optional<VerificationToken> getVerificationToken(String token) {
        return Optional.ofNullable(verificationTokenRepository.findByToken(token));
    }

    @Override
    public void deleteUsedVerificationToken(String token) {
        verificationTokenRepository.deleteAllByToken(token);
    }

    @Override
    public void activateUser(User user, String token) {
        user.setEnabled(true);
        this.saveRegisteredUser(user);
        this.deleteUsedVerificationToken(token);
    }

    @Override
    public void createRecoveryToken(User user, String token) {
        recoveryTokenRepository.save(new RecoveryToken(user, token));
    }

    @Override
    public Optional<RecoveryToken> findByRecoveryToken(String token) {
        return Optional.ofNullable(recoveryTokenRepository.findByToken(token));
    }

    @Override
    public void changeUserPassword(User user, PasswordDto passwordDto, String token) {
        user.unlock();
        final String encodedPassword = passwordEncoder.encode(passwordDto.getPassword());
        user.setPassword(encodedPassword);
        userRepository.save(user);
        recoveryTokenRepository.deleteAllByToken(token);
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

    public void setLocalePolicy(LocalePolicy localePolicy) {
        this.localePolicy = localePolicy;
    }
}
