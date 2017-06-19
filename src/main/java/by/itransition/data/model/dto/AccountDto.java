package by.itransition.data.model.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Created by ilya on 6/19/17.
 */
public class AccountDto extends PasswordDto {

    @NotNull
    private String oldPassword;

    private AccountDto() {
    }

    public static AccountDto getPlaceholder() {
        return new AccountDto("", "", "");
    }

    public AccountDto(String password, String matchingPassword, String oldPassword) {
        super(password, matchingPassword);
        this.oldPassword = oldPassword;
    }

    public String getOldPassword() {
        return oldPassword;
    }

    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
    }
}
