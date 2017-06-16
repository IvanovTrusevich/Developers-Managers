package by.itransition.data.repository;

import by.itransition.data.model.Photo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PhotoRepository extends JpaRepository<Photo, Long> {
    Photo findByImage(String image);
}