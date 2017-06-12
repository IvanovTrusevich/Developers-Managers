package by.itransition.data.repository;

import by.itransition.data.model.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Created by ilya on 5/29/17.
 */
@Repository
public interface ProjectRepository extends JpaRepository<Project, Long> {

    Project findByGitRepoName(String gitRepoName);

    @Query("SELECT p.gitLastSHA FROM Project p WHERE p.gitRepoName = :gitRepoName")
    String findGitLastSHAByGitRepoName(@Param("gitRepoName") String gitRepoName);
}
