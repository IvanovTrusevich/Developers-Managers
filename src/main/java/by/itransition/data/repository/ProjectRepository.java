package by.itransition.data.repository;

import by.itransition.data.model.Project;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by ilya on 5/29/17.
 */
public interface ProjectRepository extends JpaRepository<Project, Long> {
}
