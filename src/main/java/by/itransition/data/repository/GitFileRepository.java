package by.itransition.data.repository;

import by.itransition.data.model.GitFile;
import by.itransition.data.model.Project;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Map;

/**
 * Created by User on 12.06.2017.
 */
public interface GitFileRepository extends JpaRepository<GitFile, Long> {
    List<GitFile> findByProject(Project project);
    GitFile findByProjectAndFileName(Project project, String fileName);
    void deleteAllByProject(Project project);
}
