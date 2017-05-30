package by.itransition;

import by.itransition.data.model.User;
import by.itransition.data.repository.UserRepository;
import org.apache.log4j.Logger;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.transaction.Transactional;

@SpringBootApplication
public class DevmanApplication {
    private static final Logger log = Logger.getLogger(DevmanApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(DevmanApplication.class, args);
	}

	@Bean
	@Profile("dev")
	@Transactional
	public CommandLineRunner dev(UserRepository userRepository, PasswordEncoder passwordEncoder) {
		return (args) -> {
			String encodedPassword = passwordEncoder.encode("ilya");
			User user = new User("com.ilya.ivanov@gmail.com", encodedPassword);
			user.addAuthority(new SimpleGrantedAuthority("ROLE_ADMIN"));
			user.addAuthority(new SimpleGrantedAuthority("ROLE_USER"));
			userRepository.save(user);
			final User one = userRepository.findOne(1L);
			log.info(one);
		};
	}
}
