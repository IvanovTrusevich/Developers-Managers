package by.itransition;

import by.itransition.data.model.GitFile;
import by.itransition.data.model.User;
import by.itransition.data.repository.GitFileRepository;
import by.itransition.data.repository.ProjectRepository;
import by.itransition.data.repository.UserRepository;
import by.itransition.data.model.Project;
import by.itransition.service.github.GithubService;
import org.apache.log4j.Logger;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@SpringBootApplication
public class DevmanApplication {
    private static final Logger log = Logger.getLogger(DevmanApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(DevmanApplication.class, args);
    }

    @Bean
    //@Profile("dev")
    @Transactional
    public CommandLineRunner dev(GitFileRepository gitFileRepository,
                                 ProjectRepository projectRepository,
                                 UserRepository userRepository,
                                 PasswordEncoder passwordEncoder) {
        return (args) -> {
            //ElasticSearch.main(null);
//            String encodedPassword = passwordEncoder.encode("ilya");
//            User user = new User("com.ilya.ivanov@gmail.com", encodedPassword, "Ivanov",
//                    "Ilya","illya","Ilya1vanov","photo", "РјРѕР»РѕРґРµС†");
//            user.addAuthority(new SimpleGrantedAuthority("ROLE_ADMIN"));
//            user.addAuthority(new SimpleGrantedAuthority("ROLE_USER"));
//            userRepository.save(user);
//
//            String encodedPassword2 = passwordEncoder.encode("valik");
//            User user2 = new User("vtrusevich@gmail.com", encodedPassword2, "aa",
//                    "aaa","aaa","vtrusevich","nnnn","РЅРµ РјРѕР»РѕРґРµС†");
//            user.addAuthority(new SimpleGrantedAuthority("ROLE_ADMIN"));
//            user.addAuthority(new SimpleGrantedAuthority("ROLE_USER"));
//            userRepository.save(user2);
//
//            final User one = userRepository.findOne(1L);
//            final User two = userRepository.findOne(2L);
//            log.info(one);
//            log.info(two);
//
//            Set<User> developers = new HashSet<>();
//            developers.add(two);
//
//            Set<User> managers = new HashSet<>();
//            managers.add(one);
//
//            Project project = new Project(managers, developers,"socket",
//                    "Socket","https://github.com/ItransitionProjects/Socket.git",
//                    "lasSSH","RM",null,null);
//            projectRepository.save(project);
//
//            final Project pOne = projectRepository.findOne(1L);
//            List<GitFile> files = new ArrayList<>();
//            files.add(new GitFile("main.txt","hello",pOne));
//            gitFileRepository.save(files);
//
//            final Project pr = projectRepository.findOne(1L);
//            log.info(pr.getGitLastSHA());
//
//            log.info("lastSha " + projectRepository.findGitLastSHAByGitRepoName("Socket"));
//
//            GithubService ghi = new GithubService(projectRepository,gitFileRepository);
//            log.info("size = " + ghi.getFiles("Socket").size());
        };
    }
}
