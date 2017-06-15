package by.itransition.data.repository;

import by.itransition.data.model.RecoveryToken;
import by.itransition.data.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by ilya on 6/14/17.
 */
@Repository
public interface RecoveryTokenRepository extends JpaRepository<RecoveryToken, Long> {
    RecoveryToken findByToken(String token);
}
