package by.itransition.data.model.dto;

import lombok.*;
import by.itransition.data.constraints.annotation.PasswordMatches;
import by.itransition.data.constraints.annotation.ValidEmail;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * @author Ilya Ivanov
 */
@PasswordMatches(message = "{password.matching}")
@Value
@AllArgsConstructor
public class UserDto {
    @NotNull
    @Size(min = 2, max = 30, message = "{firstName.size}")
    private String firstName;

    @NotNull
    @Size(min = 2, max = 30, message = "{lastName.size}")
    private String lastName;

    @Size(min = 2, max = 30, message = "{lastName.size}")
    private String middleName;

    @NotNull
    @ValidEmail(message = "{email.valid}")
    private String email;

    @NotNull
    @Size(min = 2, max = 30)
    private String username;

    @NotNull
    @Size(min = 8, max = 25, message = "{password.size}")
    private String password;

    @NotNull
    @Size(min = 8, max = 25, message = "{password.size}")
    private String matchingPassword;

    public static UserDto getPlaceholder() {
        return new UserDto("", "", "", "", "", "", "");
    }
}
