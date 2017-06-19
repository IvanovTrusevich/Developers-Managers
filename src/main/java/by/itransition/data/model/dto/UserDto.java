package by.itransition.data.model.dto;

import by.itransition.data.constraints.annotation.UniqueEmail;
import by.itransition.data.constraints.annotation.UniqueUsername;
import by.itransition.data.model.Gender;
import lombok.*;
import by.itransition.data.constraints.annotation.PasswordMatches;
import by.itransition.data.constraints.annotation.ValidEmail;
import lombok.experimental.FieldDefaults;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * @author Ilya Ivanov
 */
//@Data
//@AllArgsConstructor
public class UserDto extends PasswordDto {
    @NotNull
    @Size(min = 2, max = 30, message = "{firstName.size}")
    private String firstName;

    @NotNull
    @Size(min = 2, max = 30, message = "{lastName.size}")
    private String lastName;

    @Size(max = 30, message = "{middleName.size}")
    private String middleName;

    @NotNull
    @ValidEmail(message = "{email.valid}")
    @UniqueEmail(message = "{email.unique}")
    private String email;

    @NotNull
    private Gender gender;

    @NotNull
    @Size(min = 2, max = 30, message = "{username.size}")
    @UniqueUsername(message = "{username.unique}")
    private String username;


    private MultipartFile profileImage;

    private UserDto() {
    }

    public UserDto(String firstName, String lastName, String middleName, String email, Gender gender, String username, String password, String matchingPassword) {
        super(password, matchingPassword);
        this.firstName = firstName;
        this.lastName = lastName;
        this.middleName = middleName;
        this.email = email;
        this.gender = gender;
        this.username = username;
    }

    public static UserDto getPlaceholder() {
        return new UserDto("", "", "", "", Gender.OTHER, "", "", "");
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public MultipartFile getProfileImage() {
        return profileImage;
    }

    public void setProfileImage(MultipartFile profileImage) {
        this.profileImage = profileImage;
    }
}
