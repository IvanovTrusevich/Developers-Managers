package by.itransition.data.constraints.validator;

import by.itransition.data.constraints.annotation.UniqueEmail;
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
public class EmailUniquenessValidator implements ConstraintValidator<UniqueEmail, String> {
    private UserRepository userRepository;

    @Autowired
    public EmailUniquenessValidator(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void initialize(UniqueEmail uniqueEmail) {
    }

    @Override
    public boolean isValid(String email, ConstraintValidatorContext constraintValidatorContext) {
        return userRepository.findByEmail(email) == null;
    }
}
