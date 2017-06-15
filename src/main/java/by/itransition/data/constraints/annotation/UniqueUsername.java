package by.itransition.data.constraints.annotation;

import by.itransition.data.constraints.validator.UsernameUniquenessValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Created by ilya on 6/13/17.
 */
@Target({TYPE, FIELD, ANNOTATION_TYPE})
@Retention(RUNTIME)
@Constraint(validatedBy = UsernameUniquenessValidator.class)
@Documented
public @interface UniqueUsername {
    String message() default "User with this username already exists";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
