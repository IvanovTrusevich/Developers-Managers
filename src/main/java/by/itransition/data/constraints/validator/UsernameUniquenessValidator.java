package by.itransition.data.constraints.validator;

import by.itransition.data.constraints.annotation.UniqueUsername;
import by.itransition.data.model.dto.UserDto;
import by.itransition.data.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * Created by ilya on 6/13/17.
 */
@Component
public class UsernameUniquenessValidator implements ConstraintValidator<UniqueUsername, String> {
    private UserRepository userRepository;

    @Autowired
    public UsernameUniquenessValidator(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void initialize(UniqueUsername uniqueUsername) {
    }

    @Override
    public boolean isValid(String username, ConstraintValidatorContext constraintValidatorContext) {
        return userRepository.findByUsername(username) == null;
    }
}
