package by.itransition.data.repository;

import by.itransition.data.model.Project;
import by.itransition.data.model.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

@Repository
public interface TagRepository extends JpaRepository<Tag, Long> {

    @Query("SELECT t.projects FROM Tag t WHERE t.tagName = :tagName")
    Set<Project> findProjectsByTagName(@Param("tagName") String tagName);

    Tag findByTagName(String tagName);
}
