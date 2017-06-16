package by.itransition.service.cloudinary;

import com.cloudinary.Cloudinary;
import com.cloudinary.Singleton;
import com.cloudinary.utils.ObjectUtils;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.Map;

public class CloudinaryService {

    private Cloudinary cloudinary;

    public static void main(String[] args) throws IOException {
        CloudinaryService c = new CloudinaryService();
        File f = new File("D:\\valik\\Прочее\\Мой телефон\\DCIM\\100MEDIA\\IMAG0519.jpg");
        byte[] bytes = FileUtils.readFileToByteArray(f);
        System.out.println(c.uploadFile(bytes));
    }
    public CloudinaryService(){
        cloudinary = Singleton.getCloudinary();
    }

    public String uploadFile(byte[] bytes) throws IOException {
        Map uploadResult = cloudinary.uploader().upload(bytes, ObjectUtils.emptyMap());
        return uploadResult.get("url").toString();
    }
}
