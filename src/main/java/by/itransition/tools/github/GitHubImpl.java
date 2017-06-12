package by.itransition.tools.github;

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
import org.springframework.transaction.annotation.Transactional;

public class GitHubImpl {
	private static final String orgName = "ItransitionProjects";
    private static final String oAuth = System.getenv("githubToken");
    private static final String branchName = "master";
    private ProjectRepository projectRepository;
    private GitFileRepository gitFileRepository;

    public GitHubImpl() {
    }

    public GitHubImpl(ProjectRepository projectRepository, GitFileRepository gitFileRepository) {
        this.projectRepository = projectRepository;
        this.gitFileRepository = gitFileRepository;
    }

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

	public List<Pair<String,String>> getFiles(String repoName) {

        if(isLastCommitCashed(repoName)){
            return getFilesFromCache(repoName);
        }
        List<Pair<String,String>> files = loadFilesFromGithub(repoName);
        cacheFiles(files, repoName);
        return files;
	}

    public String getGitUrl(String repoName) {
            return getRepo(repoName).getGitTransportUrl();
    }

    public String getHtmlUrl(String repoName) {
        return getRepo(repoName).getHtmlUrl().toString();
    }

//    @SuppressWarnings("deprecation")
	public String getReadMe(String repoName) {
        try {
            return getRepo(repoName).getReadme().getContent();
        } catch (IOException e) {
            return null;
        }
    }

	private GHRepository getRepo(String repoName) {
        try {
            GitHub github = GitHub.connectUsingOAuth(oAuth);
            GHOrganization organisation = github.getOrganization(orgName);
            return organisation.getRepository(repoName);
        } catch (IOException e) {
            return null;
        }
    }

    private boolean isLastCommitCashed(String repoName) {
        GHRepository repo = getRepo(repoName);
        String lastCachedCommitSha = projectRepository.findGitLastSHAByGitRepoName(repoName);
        String lastCommitSha;
        try {
            lastCommitSha = repo.getBranch(branchName).getSHA1();
        } catch (IOException e) {
            return false;
        }
        return lastCachedCommitSha.equals(lastCommitSha);
    }

    @SuppressWarnings("deprecation")
    private List<Pair<String,String>> loadFilesFromGithub(String repoName) {
        List<Pair<String,String>> files = new ArrayList<>();
        try {
            GHRepository repo = getRepo(repoName);
            GHTree tree = repo.getTreeRecursive(branchName, 1);
            List<GHTreeEntry> entries = tree.getTree();
            for (GHTreeEntry entry : entries) {
                if (entry.getType().equals("blob")) {
                    files.add(new Pair<>(entry.getPath(), repo.getFileContent(entry.getPath()).getContent()));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        return files;
    }

    private List<Pair<String,String>> getFilesFromCache(String repoName) {
        List<GitFile> files = gitFileRepository.findAll();
        List<Pair<String,String>> formattedFiles = new ArrayList<>();
        for(GitFile file : files){
            formattedFiles.add(new Pair<>(file.getFileName(), file.getFileContent()));
        }
        return formattedFiles;
    }

    @Transactional
    private void cacheFiles(List<Pair<String, String>> files, String repoName) {
	    Project project = projectRepository.findByGitRepoName(repoName);
	    project.getGitFiles().remove(2);
	    projectRepository.save(project);
        gitFileRepository.deleteAllByProject(project);
	    for(Pair<String,String> file : files){
	        GitFile gitFile = new GitFile();
	        gitFile.setFileName(file.getKey());
            gitFile.setFileContent(file.getValue());
            gitFile.setProject(project);
            gitFileRepository.save(gitFile);
        }
    }
}
