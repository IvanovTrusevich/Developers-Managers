package by.itransition.data.model;

import javax.persistence.*;

@Entity
@Table(name = "git_files")
public class GitFile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "git_file_id")
    private Long id;

    @Column(name = "file_name")
    private String fileName;

    @Column(name = "file_content")
    private String fileContent;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="project_id")
    private Project project;

    public Long getId() {
        return id;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFileContent() {
        return fileContent;
    }

    public void setFileContent(String fileContent) {
        this.fileContent = fileContent;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public GitFile(String fileName, String fileContent, Project project) {
        this.fileName = fileName;
        this.fileContent = fileContent;
        this.project = project;
    }

    public GitFile() {
    }
}