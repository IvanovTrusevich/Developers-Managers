package by.itransition.data.model.dto;

import by.itransition.data.constraints.annotation.PasswordMatches;
import by.itransition.data.constraints.annotation.ValidEmail;

import javax.validation.constraints.Size;

/**
 * @author Ilya Ivanov
 */
@PasswordMatches
public class UserDto {
    public static final UserDto PLACEHOLDER = new UserDto("", "", "");

//    @Size(min = 2, max = 30, message = "{firstName.size}")
//    private String firstName;
//
//    @Size(min = 2, max = 30, message = "{lastName.size}")
//    private String lastName;
//
//    @Size(min = 2, max = 30)
//    private String username;

    @Size(min = 5, max = 25)
    private String password;

    @Size(min = 5, max = 25, message = "{password.matching}")
    private String matchingPassword;

    @ValidEmail(message = "{email.valid}")
    private String email;

    private UserDto() {
    }

    public UserDto(String email, String password, String matchingPassword) {
        this.email = email;
        this.password = password;
        this.matchingPassword = matchingPassword;
    }

    public static UserDto getPLACEHOLDER() {
        return PLACEHOLDER;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getMatchingPassword() {
        return matchingPassword;
    }

    public void setMatchingPassword(String matchingPassword) {
        this.matchingPassword = matchingPassword;
    }
}
