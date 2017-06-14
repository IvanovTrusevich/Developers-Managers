package by.itransition.service.photo;

import by.itransition.data.model.Photo;
import by.itransition.data.model.dto.UserDto;
import org.springframework.stereotype.Service;

import java.io.IOException;

/**
 * Created by ilya on 6/14/17.
 */
@Service
public interface PhotoService {
    Photo getDefaultPhoto();
    Photo uploadFile(UserDto userDto) throws IOException;
    Photo uploadFile(byte[] bytes) throws IOException;
}
