package by.itransition.data.model.dto;

import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotNull;

/**
 * Created by ilya on 6/19/17.
 */
public class PhotoDto {
    @NotNull
    private MultipartFile profileImage;

    private PhotoDto() {
    }

    public PhotoDto(MultipartFile profileImage) {
        this.profileImage = profileImage;
    }

    public MultipartFile getProfileImage() {
        return profileImage;
    }

    public void setProfileImage(MultipartFile profileImage) {
        this.profileImage = profileImage;
    }
}
