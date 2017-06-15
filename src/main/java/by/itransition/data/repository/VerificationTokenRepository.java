package by.itransition.data.repository;

import by.itransition.data.model.VerificationToken;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by ilya on 6/15/17.
 */
public interface VerificationTokenRepository extends JpaRepository<VerificationToken, Long> {
    VerificationToken findByToken(String token);

    void deleteAllByToken(String token);
}
