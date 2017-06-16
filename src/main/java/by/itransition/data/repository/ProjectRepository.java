package by.itransition.data.repository;

import by.itransition.data.model.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;


@Repository
public interface ProjectRepository extends JpaRepository<Project, Long> {

    Project findByGitRepoName(String gitRepoName);

    @Query("SELECT p.gitLastSHA FROM Project p WHERE p.gitRepoName = :gitRepoName")
    String findGitLastSHAByGitRepoName(@Param("gitRepoName") String gitRepoName);

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query("update Project p set p.gitLastSHA =:gitLastSHA where p.gitRepoName =:gitRepoName")
    void updateProjectSHAByRepoName(@Param("gitLastSHA") String gitLastSHA, @Param("gitRepoName") String gitRepoName);
}
