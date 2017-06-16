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
public interface ProjectRepository extends JpaRepository<Project, Long> {

    Project findByGitRepoName(String gitRepoName);

    @Query("SELECT p.gitLastSHA FROM Project p WHERE p.gitRepoName = :gitRepoName")
    String findGitLastSHAByGitRepoName(@Param("gitRepoName") String gitRepoName);

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query("update Project p set p.gitLastSHA =:gitLastSHA where p.gitRepoName =:gitRepoName")
    void updateProjectSHAByRepoName(@Param("gitLastSHA") String gitLastSHA, @Param("gitRepoName") String gitRepoName);

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query("update Project p set p.gitReadme =:gitReadme where p.gitRepoName =:gitRepoName")
    void updateGitReadMeByRepoName(@Param("gitReadme") String gitReadme, @Param("gitRepoName") String gitRepoName);

    @Query("select p.gitRepoName from Project p where p.projectName =:projectName")
    String findGitRepoNameByProjectName(@Param("projectName") String projectName);

    @Query("select p.wikiContent from Project p where p.projectName =:projectName")
    String findWikiContentByProjectName(@Param("projectName") String projectName);

    @Query("select p.tags from Project p where p.projectName =:projectName")
    Set<Tag> findTagsByProjectName(@Param("projectName") String projectName);

    @Query("select p.gitReadme from Project p where p.projectName =:projectName")
    String findGitReadmeByProjectName(@Param("projectName") String projectName);
}
