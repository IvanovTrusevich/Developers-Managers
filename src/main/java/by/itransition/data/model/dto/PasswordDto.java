package by.itransition.data.model.dto;

import by.itransition.data.constraints.annotation.PasswordMatches;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Created by ilya on 6/15/17.
 */
@PasswordMatches(message = "{password.matching}")
public class PasswordDto {

    @NotNull
    @Size(min = 8, max = 25, message = "{password.size}")
    private String password;

    @NotNull
    @Size(min = 8, max = 25, message = "{password.size}")
    private String matchingPassword;

    PasswordDto() {
    }

    public static PasswordDto getPlaceholder() {
        return new PasswordDto("", "");
    }

    public PasswordDto(String password, String matchingPassword) {
        this.password = password;
        this.matchingPassword = matchingPassword;
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
