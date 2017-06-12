package by.itransition.data.model;

import javax.persistence.*;
import java.util.List;

/**
 * Created by ilya on 5/29/17.
 */
@Entity
@Table(name = "projects")
@Getter
public class Project {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "project_id")
    private Long id;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "developer_project",
            joinColumns = @JoinColumn(name = "project_id", referencedColumnName = "project_id"),
            inverseJoinColumns = @JoinColumn(name = "developers", referencedColumnName = "user_id"))
    private List<User> developers;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name="manager_project",
            joinColumns=@JoinColumn(name="project_id", referencedColumnName="project_id"),
            inverseJoinColumns=@JoinColumn(name="managers", referencedColumnName="user_id"))
    private List<User> managers;

    @Column(name = "project_name")
    private String projectName;

    @Column(name = "git_repo_name")
    private String gitRepoName;

    @Column(name = "git_repo_url")
    private String gitRepoUrl;

    @Column(name = "git_last_SHA")
    private String gitLastSHA;

    @Column(name = "git_readme")
    private String gitReadme;

    @OneToMany(mappedBy = "project")
    private List<GitFile> gitFiles;

    public Project(String projectName, String gitRepoName, List<User> managers) {
        this.projectName = projectName;
        this.gitRepoName = gitRepoName;
        this.managers = managers;
    }
}
