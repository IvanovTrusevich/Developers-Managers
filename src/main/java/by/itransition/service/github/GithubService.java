package by.itransition.service.github;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import by.itransition.data.model.GitFile;
import by.itransition.data.model.Project;
import by.itransition.data.repository.GitFileRepository;
import by.itransition.data.repository.ProjectRepository;
import org.kohsuke.github.GHCreateRepositoryBuilder;
import org.kohsuke.github.GHOrganization;
import org.kohsuke.github.GHRepository;
import org.kohsuke.github.GHTree;
import org.kohsuke.github.GHTreeEntry;
import org.kohsuke.github.GitHub;

import javafx.util.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("githubService")
public class GithubService {
	private static final String orgName = "ItransitionProjects";
    private static final String oAuth = System.getenv("githubToken");
    private static final String branchName = "master";
    private ProjectRepository projectRepository;
    private GitFileRepository gitFileRepository;
    private GitHub github;

    @Autowired
    public GithubService(ProjectRepository projectRepository,
                         GitFileRepository gitFileRepository) {
        this.projectRepository = projectRepository;
        this.gitFileRepository = gitFileRepository;
    }
//    @Autowired
//    public void setProjectRepository(ProjectRepository projectRepository) {
//        this.projectRepository = projectRepository;
//    }
//    @Autowired
//    public void setGitFileRepository(GitFileRepository gitFileRepository) {
//        this.gitFileRepository = gitFileRepository;
//    }

    public boolean createRepo(String repoName) {
		GitHub github;
		try {
			github = GitHub.connectUsingOAuth(oAuth);
			GHOrganization organisation = github.getOrganization(orgName);
			GHCreateRepositoryBuilder builder = organisation.createRepository(repoName);
			builder.downloads(true);
			builder.create();
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	public boolean deleteRepo(String repoName) {
		GitHub github;
		try {
			github = GitHub.connectUsingOAuth(oAuth);
			GHOrganization organisation = github.getOrganization(orgName);
			organisation.getRepository(repoName).delete();
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	public List<Pair<String,String>> getFiles(String repoName) throws IOException {

        if(isLastCommitCashed(repoName)){
            return getFilesFromCache(repoName);
        }
        List<Pair<String,String>> files = loadFilesFromGithub(repoName);
        cacheFiles(files, repoName);
        return files;
	}

    public String getGitUrl(String repoName) throws IOException {
            return getRepo(repoName).getGitTransportUrl();
    }

    public String getHtmlUrl(String repoName) throws IOException {
        return getRepo(repoName).getHtmlUrl().toString();
    }

    @SuppressWarnings("deprecation")
	public String getReadMe(String repoName) throws IOException {
            return getRepo(repoName).getReadme().getContent();
    }

	private GHRepository getRepo(String repoName) throws IOException {
        if(github == null)
            github = GitHub.connectUsingOAuth(oAuth);
        GHOrganization organisation = github.getOrganization(orgName);
        return organisation.getRepository(repoName);
    }

    private boolean isLastCommitCashed(String repoName) throws IOException {

        String lastCachedCommitSha = projectRepository.findGitLastSHAByGitRepoName(repoName);
        String lastCommitSha = getLastCommitSha(repoName);
        return lastCachedCommitSha.equals(lastCommitSha);
    }

    private String getLastCommitSha(String repoName) throws IOException {
        GHRepository repo = getRepo(repoName);
        return repo.getBranch(branchName).getSHA1();
    }


    @SuppressWarnings("deprecation")
    private List<Pair<String,String>> loadFilesFromGithub(String repoName) throws IOException {
        List<Pair<String,String>> files = new  ArrayList<>();
        GHRepository repo = getRepo(repoName);
        GHTree tree = repo.getTreeRecursive(branchName, 1);
        List<GHTreeEntry> entries = tree.getTree();
        for (GHTreeEntry entry : entries) {
            if (entry.getType().equals("blob")) {
                files.add(new Pair<>(entry.getPath(), repo.getFileContent(entry.getPath()).getContent()));
            }
        }
        return files;
    }

    private List<Pair<String,String>> getFilesFromCache(String repoName) {
	    Project project = projectRepository.findByGitRepoName(repoName);
        List<GitFile> files = gitFileRepository.findByProject(project);
        List<Pair<String,String>> formattedFiles = new ArrayList<>();
        for(GitFile file : files)
            formattedFiles.add(new Pair<>(file.getFileName(), file.getFileContent()));
        return formattedFiles;
    }

    private void cacheFiles(List<Pair<String, String>> files, String repoName) throws IOException {
	    Project project = projectRepository.findByGitRepoName(repoName);
        gitFileRepository.deleteByProject(project);
	    for(Pair<String,String> file : files){
	        GitFile gitFile = new GitFile();
	        gitFile.setFileName(file.getKey());
            gitFile.setFileContent(file.getValue());
            gitFile.setProject(project);
            gitFileRepository.save(gitFile);
        }
        projectRepository.updateProjectSHAByRepoName(getLastCommitSha(repoName),repoName);
    }
}
