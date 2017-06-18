package by.itransition;

import by.itransition.data.model.*;
import by.itransition.data.repository.GitFileRepository;
import by.itransition.data.repository.ProjectRepository;
import by.itransition.data.repository.TagRepository;
import by.itransition.data.repository.UserRepository;
import by.itransition.service.elasticsearch.SynchronizationService;
import by.itransition.service.photo.PhotoService;
import org.apache.log4j.Logger;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

@SpringBootApplication
public class DevmanApplication {
    private static final Logger log = Logger.getLogger(DevmanApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(DevmanApplication.class, args);
	}

	@Bean
    @Profile("prod")
    public CommandLineRunner prod(GitFileRepository gitFileRepository,
                                  ProjectRepository projectRepository,
                                  UserRepository userRepository,
                                  PasswordEncoder passwordEncoder,
                                  PhotoService cloudinaryService,
                                  TagRepository tagRepository,
                                  SynchronizationService synchronizationService) {
        return (args) -> {
            addAdmins(userRepository, passwordEncoder, cloudinaryService);
            addProject(userRepository, projectRepository, gitFileRepository, synchronizationService);
            addTags( projectRepository, tagRepository);
//            backendElasticSearchSynchronize(synchronizationService);
        };
	}

    @Bean
	@Profile("dev")
	public CommandLineRunner dev(GitFileRepository gitFileRepository,
                                 ProjectRepository projectRepository,
                                 UserRepository userRepository,
                                 PasswordEncoder passwordEncoder,
                                 PhotoService cloudinaryService,
                                 SynchronizationService synchronizationService) {
		return (args) -> {
            addAdmins(userRepository, passwordEncoder, cloudinaryService);
            addProject(userRepository, projectRepository, gitFileRepository, synchronizationService);
		};
	}

    private void addProject(UserRepository userRepository, ProjectRepository projectRepository,
                            GitFileRepository gitFileRepository, SynchronizationService synchronizationService) throws IOException {
        final User one = userRepository.findOne(1L);
        final User two = userRepository.findOne(2L);
        log.info(one);
        log.info(two);

        Set<User> developers = new HashSet<>();
        developers.add(two);

        Set<User> managers = new HashSet<>();
        managers.add(one);

        Project project = new Project(managers, developers,"socket",
                "Socket","https://github.com/ItransitionProjects/Socket.git",
                "lasSSH","RM",null,null,null);
        projectRepository.save(project);

//        final Project pOne = projectRepository.findOne(1L);
//        List<GitFile> files = new ArrayList<>();
//        files.add(new GitFile("main.txt","hello",pOne));
//        gitFileRepository.save(files);
//
//        final Project pr = projectRepository.findOne(1L);
//        log.info(pr.getGitLastSHA());
//
//        log.info("lastSha " + projectRepository.findGitLastSHAByGitRepoName("Socket"));
//
//        GithubService ghi = new GithubService(projectRepository,gitFileRepository);
//        log.info("size = " + ghi.getFiles("Socket").size());

	}

    private void addAdmins(UserRepository userRepository, PasswordEncoder passwordEncoder, PhotoService cloudinaryService) {
	    if (!userRepository.exists(1L)) {
            final Photo defaultPhoto = cloudinaryService.getDefaultPhoto();
            List<GrantedAuthority> authorities = new ArrayList<>();
            authorities.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
            authorities.add(new SimpleGrantedAuthority("ROLE_MANAGER"));
            authorities.add(new SimpleGrantedAuthority("ROLE_DEVELOPER"));

            String encodedPassword = passwordEncoder.encode("Ilyailya1");
            User user = new User("com.ilya.ivanov@gmail.com", encodedPassword, authorities, "Ivanov",
                    "Ilya", "Petrovich", "Ilya", defaultPhoto);
            user.setEnabled(true);
            userRepository.save(user);

            String encodedPassword2 = passwordEncoder.encode("Valikvalik1");
            User user2 = new User("vtrusevich@gmail.com", encodedPassword2, authorities, "aa",
                    "aaa", "aaa", "aaa", defaultPhoto);
            user2.setEnabled(true);
            userRepository.save(user2);
        }
    }

    private void backendElasticSearchSynchronize(SynchronizationService synchronizationService) {
        Executors.newSingleThreadScheduledExecutor(r -> {
            Thread thread = Executors.defaultThreadFactory().newThread(r);
            thread.setDaemon(true);
            return thread;
        }).scheduleAtFixedRate(synchronizationService::synchronizeWithSql, 1, 3, TimeUnit.MINUTES);
    }

    private void addTags(ProjectRepository projectRepository, TagRepository tagRepository) {
        //Tag tag = new Tag();
    }
}
