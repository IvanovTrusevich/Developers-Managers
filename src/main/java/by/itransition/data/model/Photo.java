package by.itransition.data.model;

import com.cloudinary.Singleton;
import com.cloudinary.StoredFile;
import com.cloudinary.Transformation;
import org.springframework.beans.factory.annotation.Value;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by ilya on 6/14/17.
 */
@Entity(name = "photos")
public class Photo {
    @Value("${by.itransition.service.photo.thumbnail.width}")
    private static Double width;

    @Value("${by.itransition.service.photo.thumbnail.height}")
    private static Double height;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "image")
    private String image;

    @Transient
    private String thumbnail;

    @Column(name = "created_at")
    @Temporal(value = TemporalType.TIMESTAMP)
    private Date createdAt = new Date();

    public static Photo createStaticPhoto(String staticUrl) {
        final Photo photo = new Photo();
        photo.setImage(staticUrl);
        photo.setThumbnail(staticUrl);
        return photo;
    }

    private Photo() {
    }

    public Photo(StoredFile file) {
        this.setUpload(file);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getImage() {
        return image;
    }

    public String getThumbnail(Double width, Double height) {
        return getFormattedThumbnail(width, height);
    }

    public String getThumbnail() {
        if (thumbnail == null) {
            return getFormattedThumbnail(width, height);
        } else return thumbnail;
    }

    private String getFormattedThumbnail(Double width, Double height) {
        final StoredFile file = getUpload();
        return Singleton.getCloudinary().url()
                .resourceType(file.getResourceType())
                .type(file.getType())
                .format(file.getFormat())
                .version(file.getVersion())
                .transformation(new Transformation().width(width).height(height).crop("fit"))
                .generate(file.getPublicId());
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt =  createdAt;
    }

    public StoredFile getUpload() {
        StoredFile file = new StoredFile();
        file.setPreloadedFile(image);
        return file;
    }

    public void setUpload(StoredFile file) {
        this.image = file.getPreloadedFile();
    }

    @Override
    public String toString() {
        return "Photo{" +
                "id=" + id +
                ", image='" + image + '\'' +
                ", createdAt=" + createdAt +
                '}';
    }
}
