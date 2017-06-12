package by.itransition.data.model;

import lombok.*;

import javax.persistence.*;
import java.util.List;

/**
 * Created by ilya on 5/29/17.
 */
@Entity
@Table(name = "projects")
//@Getter
//@Setter
//@EqualsAndHashCode
//@ToString(of = {"id", "projectName", "gitRepoName", "gitRepoUrl", "gitLastSHA", "gitReadme"})
public class Project {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "project_id")
//    @Setter(AccessLevel.NONE)
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

    private Project() {
    }

    public Project(String projectName, String gitRepoName, List<User> managers) {
        this.projectName = projectName;
        this.gitRepoName = gitRepoName;
        this.managers = managers;
    }

    public Project(List<User> developers, List<User> managers, String projectName, String gitRepoName, String gitRepoUrl, String gitLastSHA, String gitReadme, List<GitFile> gitFiles) {
        this.developers = developers;
        this.managers = managers;
        this.projectName = projectName;
        this.gitRepoName = gitRepoName;
        this.gitRepoUrl = gitRepoUrl;
        this.gitLastSHA = gitLastSHA;
        this.gitReadme = gitReadme;
        this.gitFiles = gitFiles;
    }

    public Long getId() {
        return id;
    }

    public List<User> getDevelopers() {
        return developers;
    }

    public void setDevelopers(List<User> developers) {
        this.developers = developers;
    }

    public List<User> getManagers() {
        return managers;
    }

    public void setManagers(List<User> managers) {
        this.managers = managers;
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

    public String getGitRepoUrl() {
        return gitRepoUrl;
    }

    public void setGitRepoUrl(String gitRepoUrl) {
        this.gitRepoUrl = gitRepoUrl;
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

    public List<GitFile> getGitFiles() {
        return gitFiles;
    }

    public void setGitFiles(List<GitFile> gitFiles) {
        this.gitFiles = gitFiles;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Project project = (Project) o;

        if (id != null ? !id.equals(project.id) : project.id != null) return false;
        if (gitRepoName != null ? !gitRepoName.equals(project.gitRepoName) : project.gitRepoName != null) return false;
        return gitRepoUrl != null ? gitRepoUrl.equals(project.gitRepoUrl) : project.gitRepoUrl == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (gitRepoName != null ? gitRepoName.hashCode() : 0);
        result = 31 * result + (gitRepoUrl != null ? gitRepoUrl.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Project{" +
                "id=" + id +
                ", projectName='" + projectName + '\'' +
                ", gitRepoName='" + gitRepoName + '\'' +
                ", gitRepoUrl='" + gitRepoUrl + '\'' +
                ", gitLastSHA='" + gitLastSHA + '\'' +
                ", gitReadme='" + gitReadme + '\'' +
                '}';
    }
}
