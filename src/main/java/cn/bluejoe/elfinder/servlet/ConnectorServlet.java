package cn.bluejoe.elfinder.servlet;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletConfig;

import cn.bluejoe.elfinder.controller.executor.CommandExecutorFactory;
import cn.bluejoe.elfinder.controller.executor.DefaultCommandExecutorFactory;
import cn.bluejoe.elfinder.controller.executors.MissingCommandExecutor;
import cn.bluejoe.elfinder.impl.DefaultFsService;
import cn.bluejoe.elfinder.impl.DefaultFsServiceConfig;
import cn.bluejoe.elfinder.impl.FsSecurityCheckForAll;
import cn.bluejoe.elfinder.impl.StaticFsServiceFactory;
import cn.bluejoe.elfinder.localfs.LocalFsItem;
import cn.bluejoe.elfinder.localfs.LocalFsVolume;
import cn.bluejoe.elfinder.service.FsItem;
import by.itransition.service.github.GithubService;
import javafx.util.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ConnectorServlet{
    private GithubService githubService;

    @Autowired
    public void setGithubService(GithubService githubService) {
        this.githubService = githubService;
    }

	public CommandExecutorFactory createCommandExecutorFactory(ServletConfig config) {
		DefaultCommandExecutorFactory defaultCommandExecutorFactory = new DefaultCommandExecutorFactory();
		defaultCommandExecutorFactory.setClassNamePattern("cn.bluejoe.elfinder.controller.executors.%sCommandExecutor");
		defaultCommandExecutorFactory.setFallbackCommand(new MissingCommandExecutor());
		return defaultCommandExecutorFactory;
	}

	public DefaultFsService createFsService(String repoName) {
		DefaultFsService fsService = new DefaultFsService();
		fsService.setSecurityChecker(new FsSecurityCheckForAll());

		DefaultFsServiceConfig serviceConfig = new DefaultFsServiceConfig();
		serviceConfig.setTmbWidth(80);

		fsService.setServiceConfig(serviceConfig);
		LocalFsVolume volA = null;
		try {
			volA = addFiles(fsService, repoName);
		} catch (IOException e) {
			e.printStackTrace();
		}
		fsService.addVolume("B", volA);

		return fsService;
	}

	private LocalFsVolume addFiles(DefaultFsService fsService, String repoName) throws IOException {
		LocalFsVolume volA = createLocalFsVolume(repoName , new File("/B"));

		List<Pair<String, String>> files = githubService.getFiles(repoName);
		List<String> folders = makeFolders(files);
		mountAllFolders(fsService, repoName, folders);

		for (int i = 0; i < files.size(); i++) {
			File f = new File("/B/" + files.get(i).getKey());
			try {
				BufferedWriter out = new BufferedWriter(new FileWriter(f));
				out.write(files.get(i).getValue());
				out.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			FsItem it = new LocalFsItem(volA, f);
			try {
				volA.createFile(it);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return volA;
	}

	private List<String> makeFolders(List<Pair<String, String>> files) {
		List<String> folders = new ArrayList<String>();
		for (Pair<String, String> file : files) {
			String str = file.getKey();
			int index = str.lastIndexOf('/');
			if(index == -1 || folders.contains(str.substring(0, index)))
				continue;
			folders.add(str.substring(0, index));
		}
		return folders;
	}

	private void mountAllFolders(DefaultFsService fsService, String repoName, List<String> folders) {
		for(String folder : folders){
			fsService.addVolume("B", createLocalFsVolume(repoName, new File("/B/" + folder)));
		}
	}

	private LocalFsVolume createLocalFsVolume(String name, File rootDir) {
		LocalFsVolume localFsVolume = new LocalFsVolume();
		localFsVolume.setName(name);
		localFsVolume.setRootDir(rootDir);
		return localFsVolume;
	}

	public StaticFsServiceFactory createServiceFactory(ServletConfig config, String repoName) {
		StaticFsServiceFactory staticFsServiceFactory = new StaticFsServiceFactory();
		DefaultFsService fsService = createFsService(repoName);

		staticFsServiceFactory.setFsService(fsService);
		return staticFsServiceFactory;
	}

}
