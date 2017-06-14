package by.itransition.data.model.dto;

import by.itransition.data.constraints.annotation.ValidEmail;

import javax.validation.constraints.NotNull;

/**
 * Created by ilya on 6/14/17.
 */
public class LostDto {
    @NotNull
    @ValidEmail(message = "{email.valid}")
    private String credentials;

    private LostDto() {
    }

    public static LostDto getPlaceholder() {
        return new LostDto("");
    }

    public LostDto(String credentials) {
        this.credentials = credentials;
    }

    public String getCredentials() {
        return credentials;
    }

    public void setCredentials(String credentials) {
        this.credentials = credentials;
    }

    @Override
    public String toString() {
        return "LostDto{" +
                "credentials='" + credentials + '\'' +
                '}';
    }
}
