package by.itransition.data.model;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "projects")
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
    private Set<User> developers;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name="manager_project",
            joinColumns=@JoinColumn(name="project_id", referencedColumnName="project_id"),
            inverseJoinColumns=@JoinColumn(name="managers", referencedColumnName="user_id"))
    private Set<User> managers;

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
    private Set<GitFile> gitFiles;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name="project_tag",
            joinColumns=@JoinColumn(name="project_id", referencedColumnName="project_id"),
            inverseJoinColumns=@JoinColumn(name="tags", referencedColumnName="tag_id"))
    private Set<Tag> tags;

    public Project() {
    }

    public Project(Set<User> managers, Set<User> developers, String projectName,
                   String gitRepoName, String gitRepoUrl, String gitLastSHA, String gitReadme,
                   Set<GitFile> gitFiles, Set<Tag> tags) {
        this.developers = developers;
        this.projectName = projectName;
        this.gitRepoName = gitRepoName;
        this.gitRepoUrl = gitRepoUrl;
        this.gitLastSHA = gitLastSHA;
        this.gitReadme = gitReadme;
        this.gitFiles = gitFiles;
        this.managers = managers;
        this.tags = tags;
    }

    public Project(String projectName, String gitRepoName, Set<User> managers) {
        this.projectName = projectName;
        this.gitRepoName = gitRepoName;
        this.managers = managers;
    }

    public Long getId() {
        return id;
    }

    public Set<User> getManagers() {
        return managers;
    }

    public void setManagers(Set<User> managers) {
        this.managers = managers;
    }

    public Set<User> getDevelopers() {
        return developers;
    }

    public void setDevelopers(Set<User> developers) {
        this.developers = developers;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getGitRepoName() {
        return gitRepoName;
    }

    public void setGitRepoName(String gitRepoName) {
        this.gitRepoName = gitRepoName;
    }

    public String getGitLastSHA() {
        return gitLastSHA;
    }

    public void setGitLastSHA(String gitLastSHA) {
        this.gitLastSHA = gitLastSHA;
    }

    public String getGitReadme() {
        return gitReadme;
    }

    public void setGitReadme(String gitReadme) {
        this.gitReadme = gitReadme;
    }

    public Set<GitFile> getGitFiles() {
        return gitFiles;
    }

    public void setGitFiles(Set<GitFile> gitFiles) {
        this.gitFiles = gitFiles;
    }

    public String getGitRepoUrl() {
        return gitRepoUrl;
    }

    public void setGitRepoUrl(String gitRepoUrl) {
        this.gitRepoUrl = gitRepoUrl;
    }

    public Set<Tag> getTags() {
        return tags;
    }

    public void setTags(Set<Tag> tags) {
        this.tags = tags;
    }
}