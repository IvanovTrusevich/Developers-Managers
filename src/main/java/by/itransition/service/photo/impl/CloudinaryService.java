package by.itransition.service.photo.impl;

import by.itransition.data.model.Photo;
import by.itransition.data.model.dto.UserDto;
import by.itransition.data.repository.PhotoRepository;
import by.itransition.service.photo.PhotoService;
import com.cloudinary.Cloudinary;
import com.cloudinary.Singleton;
import com.cloudinary.StoredFile;
import com.cloudinary.utils.ObjectUtils;
import com.google.common.collect.Maps;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Map;

/**
 * Created by ilya on 6/14/17.
 */
@Service
@SuppressWarnings("unchecked")
public class CloudinaryService implements PhotoService {
    private PhotoRepository photoRepository;

    private Photo defaultUserIcon;

    private Cloudinary cloudinary;

    @Autowired
    public CloudinaryService(@Value("${by.itransition.service.photo.defaultUserIconUrl}") String defaultUserIconUrl, PhotoRepository photoRepository) {
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
            final Map uploadResult = cloudinary.uploader().upload(bytes, Maps.newConcurrentMap());
            return photoRepository.save(new Photo(uploadResult));
        } else return defaultUserIcon;
    }

}
