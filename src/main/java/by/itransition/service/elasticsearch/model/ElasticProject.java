package by.itransition.service.elasticsearch.model;

import by.itransition.data.model.Project;

public class ElasticProject implements ElasticModelInterface{

    private Long id;
    private String projectName;
    private String gitRepoName;
    private String gitRepoUrl;
    private String wikiContent;


    public ElasticProject(Project project) {
        this.id = project.getId();
        this.projectName = project.getProjectName();
        this.gitRepoName = project.getGitRepoName();
        this.gitRepoUrl = project.getGitRepoUrl();
        this.wikiContent = project.getWikiContent();
    }

    @Override
    public Long getId() {
        return id;
    }

    public String getProjectName() {
        return projectName;
    }

    public String getGitRepoName() {
        return gitRepoName;
    }

    public String getGitRepoUrl() {
        return gitRepoUrl;
    }

    public String getWikiContent() {
        return wikiContent;
    }
}
