package by.itransition.data.model.dto;

import by.itransition.data.model.Gender;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Created by ilya on 6/19/17.
 */
public class ProfileDto {

    @NotNull
    @Size(min = 2, max = 30, message = "{firstName.size}")
    private String firstName;

    @NotNull
    @Size(min = 2, max = 30, message = "{lastName.size}")
    private String lastName;

    @Size(max = 30, message = "{middleName.size}")
    private String middleName;

    @NotNull
    private Gender gender;

    private ProfileDto() {
    }

    public ProfileDto(String firstName, String lastName, String middleName, Gender gender) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.middleName = middleName;
        this.gender = gender;
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

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }
}
