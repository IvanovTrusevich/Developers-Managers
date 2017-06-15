package by.itransition.data.constraints.validator;

import by.itransition.data.constraints.annotation.PasswordMatches;
import by.itransition.data.model.dto.PasswordDto;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * @author Ilya Ivanov
 */
public class PasswordMatchesValidator implements ConstraintValidator<PasswordMatches, Object> {
    @Override
    public void initialize(PasswordMatches constraintAnnotation) {
    }

    @Override
    public boolean isValid(Object obj, ConstraintValidatorContext context){
        PasswordDto user = (PasswordDto) obj;
        return user.getPassword().equals(user.getMatchingPassword());
    }
}
