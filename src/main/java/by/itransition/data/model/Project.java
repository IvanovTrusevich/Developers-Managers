package by.itransition.data.model;

import lombok.*;

import javax.persistence.*;
import java.util.Set;

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

    @Column(name = "git_repo_name", unique = true)
    private String gitRepoName;

    @Column(name = "git_repo_url", unique = true)
    private String gitRepoUrl;

    @Column(name = "git_last_SHA")
    private String gitLastSHA;

    @Column(name = "enabled")
    private Boolean enabled;

    @Column(name = "git_readme", length = 10000)
    private String gitReadme;

    @Column(name = "wiki_content", length = 10000)
    private String wikiContent;

    @OneToMany(mappedBy = "project")
    private Set<GitFile> gitFiles;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="wikiLastEditor")
    private User wikiLastEditor;

    @ManyToMany(mappedBy = "projects")
    private Set<Tag> tags;

    @OneToMany(mappedBy = "projectNews")
    private Set<News> news;

    private Project() {
    }

    public Project(Set<User> managers, Set<User> developers, String projectName,
                   String gitRepoName, String gitRepoUrl, String gitLastSHA, String gitReadme,
                   Set<GitFile> gitFiles, String wikiContent, Boolean enabled) {
        this.developers = developers;
        this.managers = managers;
        this.projectName = projectName;
        this.gitRepoName = gitRepoName;
        this.gitRepoUrl = gitRepoUrl;
        this.gitLastSHA = gitLastSHA;
        this.gitReadme = gitReadme;
        this.gitFiles = gitFiles;
        this.managers = managers;
        this.wikiContent = wikiContent;
        this.enabled = enabled;
    }

    public Project(String projectName, String gitRepoName, Set<User> managers, boolean enabled) {
        this.projectName = projectName;
        this.gitRepoName = gitRepoName;
        this.managers = managers;
        this.enabled = enabled;
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

    public Set<GitFile> getGitFiles() {
        return gitFiles;
    }

    public void setGitFiles(Set<GitFile> gitFiles) {
        this.gitFiles = gitFiles;
    }

    public String getWikiContent() {
        return wikiContent;
    }

    public void setWikiContent(String wikiContent) {
        this.wikiContent = wikiContent;
    }

    public Set<News> getNews() {
        return news;
    }

    public void setNews(Set<News> news) {
        this.news = news;
    }

    public Set<Tag> getTags() {
        return tags;
    }

    public void setTags(Set<Tag> tags) {
        this.tags = tags;
    }

    public User getWikiLastEditor() {
        return wikiLastEditor;
    }

    public void setWikiLastEditor(User wikiLastEditor) {
        this.wikiLastEditor = wikiLastEditor;
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
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
        return "project{" +
                "id=" + id +
                ", projectName='" + projectName + '\'' +
                ", gitRepoName='" + gitRepoName + '\'' +
                ", gitRepoUrl='" + gitRepoUrl + '\'' +
                ", gitLastSHA='" + gitLastSHA + '\'' +
                ", gitReadme='" + gitReadme + '\'' +
                '}';
    }
}