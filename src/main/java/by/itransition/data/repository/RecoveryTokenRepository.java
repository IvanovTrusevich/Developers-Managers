package by.itransition.data.repository;

import by.itransition.data.model.RecoveryToken;
import by.itransition.data.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by ilya on 6/14/17.
 */
public interface RecoveryTokenRepository extends JpaRepository<RecoveryToken, Long> {
    RecoveryToken findByToken(String token);
}
