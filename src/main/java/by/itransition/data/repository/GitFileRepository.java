package by.itransition.data.repository;

import by.itransition.data.model.GitFile;
import by.itransition.data.model.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
@Repository
public interface GitFileRepository extends JpaRepository<GitFile, Long> {
    List<GitFile> findByProject(Project project);
    void deleteByProject(Project project);
}
