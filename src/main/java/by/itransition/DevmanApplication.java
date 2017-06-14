package by.itransition;

import by.itransition.data.model.GitFile;
import by.itransition.data.model.User;
import by.itransition.data.repository.GitFileRepository;
import by.itransition.data.repository.ProjectRepository;
import by.itransition.data.repository.UserRepository;
import by.itransition.data.model.Project;
import by.itransition.tools.github.GitHubImpl;
import javafx.util.Pair;
import org.apache.log4j.Logger;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.core.env.Environment;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

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
                                 PasswordEncoder passwordEncoder) {
        return (args) -> {
            addAdmins(userRepository, passwordEncoder);
        };
	}

	@Bean
	@Profile("dev")
	public CommandLineRunner dev(GitFileRepository gitFileRepository,
                                 ProjectRepository projectRepository,
                                 UserRepository userRepository,
                                 PasswordEncoder passwordEncoder) {
		return (args) -> {
            addAdmins(userRepository, passwordEncoder);
//			final User one = userRepository.findOne(1L);
//            final User two = userRepository.findOne(2L);
//			log.info(one);
//            log.info(two);
//
//            List<User> developers = new ArrayList<>();
//            developers.add(two);
//
//            List<User> managers = new ArrayList<>();
//            managers.add(one);
//
//            Project project = new Project(managers, developers,"socket",
//                    "Socket","https://github.com/ItransitionProjects/Socket.git",
//                    "lasSSH","RM",null);
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
//            GitHubImpl ghi = new GitHubImpl(projectRepository,gitFileRepository);
//            log.info("size = " + ghi.getFiles("Socket").size());
		};
	}

	private void addAdmins(UserRepository userRepository,
                           PasswordEncoder passwordEncoder) {
        String encodedPassword = passwordEncoder.encode("Ilyailya1");
        User user = new User("com.ilya.ivanov@gmail.com", encodedPassword, "Ivanov",
                "Ilya","illya","Ilya1vanov","photo");
        user.addAuthority(new SimpleGrantedAuthority("ROLE_ADMIN"));
        user.addAuthority(new SimpleGrantedAuthority("ROLE_USER"));
        userRepository.save(user);

        String encodedPassword2 = passwordEncoder.encode("valik");
        User user2 = new User("vtrusevich@gmail.com", encodedPassword2, "aa",
                "aaa","aaa","vtrusevich","nnnn");
        user.addAuthority(new SimpleGrantedAuthority("ROLE_ADMIN"));
        user.addAuthority(new SimpleGrantedAuthority("ROLE_USER"));
        userRepository.save(user2);
    }
}
