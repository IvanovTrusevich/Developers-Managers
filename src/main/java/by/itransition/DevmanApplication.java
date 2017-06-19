package by.itransition;

import by.itransition.data.model.*;
import by.itransition.data.repository.*;
import by.itransition.service.project.ProjectService;
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
import java.util.*;
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
                                  ProjectService projectService,
                                  NewsRepository newsRepository,
                                  SynchronizationService synchronizationService) {
        return (args) -> {

//            addAdmins(userRepository, passwordEncoder, cloudinaryService);
//            addProject(userRepository, projectRepository, gitFileRepository, synchronizationService);
//            addTags( projectRepository, tagRepository);
//            addNews(newsRepository, userRepository, projectRepository);
//            projectService.addTagToProject("secondProject","data",4);
            // backendElastickSerachSynchronizer(synchronizationService);

        };
    }


    @Bean
    @Profile("dev")
    public CommandLineRunner dev(GitFileRepository gitFileRepository,
                                 ProjectRepository projectRepository,
                                 UserRepository userRepository,
                                 PasswordEncoder passwordEncoder,
                                 PhotoService cloudinaryService,
                                 TagRepository tagRepository,
                                 NewsRepository newsRepository,
                                 SynchronizationService synchronizationService) {
        return (args) -> {
            //addAdmins(userRepository, passwordEncoder, cloudinaryService);
            addProject(userRepository, projectRepository, gitFileRepository, synchronizationService);
            addTags(projectRepository, tagRepository);

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

        Project project = new Project(managers, developers, "socket",
                "Socket", "https://github.com/ItransitionProjects/Socket.git",
                "lasSSH", "RMewff", null, null, true);
        projectRepository.save(project);

        Project project1 = new Project(managers, developers, "secondProject",
                "Notes", "https://github.com/ItransitionProjects/Notes.git",
                "lasSwefweSH", "RMewff", null, null, true);
        projectRepository.save(project1);

//        final project pOne = projectRepository.findOne(1L);
//        List<GitFile> files = new ArrayList<>();
//        files.add(new GitFile("main.txt","hello",pOne));
//        gitFileRepository.save(files);
//
//        final project pr = projectRepository.findOne(1L);
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
            List<GrantedAuthority> authorities1 = new ArrayList<>();
            authorities1.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
            authorities1.add(new SimpleGrantedAuthority("ROLE_MANAGER"));
            authorities1.add(new SimpleGrantedAuthority("ROLE_DEVELOPER"));

            List<GrantedAuthority> authorities2 = new ArrayList<>();
            authorities2.add(new SimpleGrantedAuthority("ROLE_DEVELOPER"));

            String encodedPassword = passwordEncoder.encode("Ilyailya1");
            User user = new User("com.ilya.ivanov@gmail.com", encodedPassword, authorities1, "Ivanov",
                    "Ilya", "Petrovich", "Ilya", defaultPhoto);
            user.setEnabled(true);
            userRepository.save(user);

            String encodedPassword2 = passwordEncoder.encode("Valikvalik1");
            User user2 = new User("vtrusevich@gmail.com", encodedPassword2, authorities2, "aa",
                    "aaa", "aaa", "aaa", defaultPhoto);
            user2.setEnabled(true);
            userRepository.save(user2);
        }
    }

    private void backendElastickSerachSynchronizer(SynchronizationService synchronizationService) {
        Executors.newSingleThreadScheduledExecutor(r -> {
            Thread thread = Executors.defaultThreadFactory().newThread(r);
            thread.setDaemon(true);
            return thread;
        }).scheduleAtFixedRate(() -> {
            synchronizationService.synchronizeWithSql();
        }, 1, 10, TimeUnit.MINUTES);
    }

    private void addTags(ProjectRepository projectRepository, TagRepository tagRepository) {
        Set<Project> projects = new HashSet<>();
        projects.add(projectRepository.findOne(1l));
        tagRepository.save(new Tag("сокет", 7.4, projects));
        tagRepository.save(new Tag("transport", 5.4, projects));
        tagRepository.save(new Tag("labs", 13.4, projects));
        tagRepository.save(new Tag("socket", 7.4, projects));
    }

    private void addNews(NewsRepository newsRepository, UserRepository userRepository,
                         ProjectRepository projectRepository) {
        User u1 = userRepository.findOne(1l);
        User u2 = userRepository.findOne(2l);
        Project p1 = projectRepository.findOne(1l);
        Project p2 = projectRepository.findOne(2l);
        try {
            News n1 = new News(NewsType.DEVELOPER_FIRE, u1, null, new Date());
            newsRepository.save(n1);
            Thread.sleep(1000);
            News n2 = new News(NewsType.PROJECT_ARCHIVED, u1, p1, new Date());
            newsRepository.save(n2);
            Thread.sleep(1000);
            News n3 = new News(NewsType.DEVELOPER_TO_PROJECT, u2, p2, new Date());
            newsRepository.save(n3);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
