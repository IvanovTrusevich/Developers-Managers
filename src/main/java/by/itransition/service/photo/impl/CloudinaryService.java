package by.itransition.service.photo.impl;

import by.itransition.data.model.Photo;
import by.itransition.data.model.dto.UserDto;
import by.itransition.data.repository.PhotoRepository;
import by.itransition.service.photo.PhotoService;
import com.cloudinary.Cloudinary;
import com.cloudinary.Singleton;
import com.cloudinary.StoredFile;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Map;

/**
 * Created by ilya on 6/14/17.
 */
@SuppressWarnings("unchecked")
public class CloudinaryService implements PhotoService {
    private PhotoRepository photoRepository;

    private Photo defaultUserIcon;

    private Cloudinary cloudinary;

    @Autowired
    public CloudinaryService(@Value("${by.itransition.service.cloudinary.defaultUserIconUrl}") String defaultUserIconUrl, PhotoRepository photoRepository) {
        this.photoRepository = photoRepository;
        cloudinary = Singleton.getCloudinary();
        Photo byImage = photoRepository.findByImage(defaultUserIconUrl);
        if (byImage == null)
            byImage = photoRepository.save(Photo.createStaticPhoto(defaultUserIconUrl));
        this.defaultUserIcon = byImage;
    }

    @Override
    public Photo getDefaultPhoto() {
        return defaultUserIcon;
    }

    @Override
    public Photo uploadFile(UserDto userDto) throws IOException {
        return uploadFile(userDto.getProfileImage().getBytes());
    }

    @Override
    public Photo uploadFile(byte[] bytes) throws IOException {
        if (bytes != null && bytes.length != 0) {
            final Map uploadResult = cloudinary.uploader().upload(bytes, ObjectUtils.asMap("resource_type", "auto"));
            final StoredFile storedFile = createStoredFile(uploadResult);
            return photoRepository.save(new Photo(storedFile));
        } else return defaultUserIcon;
    }

    private StoredFile createStoredFile(Map uploadResult) {
        final StoredFile storedFile = new StoredFile();
        storedFile.setPublicId((String) uploadResult.get("public_id"));
        Object version = uploadResult.get("version");
        if (version instanceof Integer) {
            storedFile.setVersion(new Long((Integer) version));
        } else {
            storedFile.setVersion((Long) version);
        }
        storedFile.setSignature((String) uploadResult.get("signature"));
        storedFile.setFormat((String) uploadResult.get("format"));
        storedFile.setResourceType((String) uploadResult.get("resource_type"));
        return storedFile;
    }
}
