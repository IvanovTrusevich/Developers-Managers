package by.itransition.data.repository;

import by.itransition.data.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Ilya Ivanov
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findByEmailOrUsername(String email, String username);
    User findByEmail(String email);
    User findByUsername(String usename);

    @Transactional
    @Query("update User u set u.firstName =:firstName where u.id =:id")
    void updateFirstNameById(@Param("firstName") String firstName, @Param("id") Long id);

    @Transactional
    @Query("update User u set u.lastName =:lastName where u.id =:id")
    void updateLastNameById(@Param("lastName") String lastName, @Param("id") Long id);

    @Transactional
    @Query("update User u set u.middleName =:middleName where u.id =:id")
    void updateMiddleNameById(@Param("middleName") String middleName, @Param("id") Long id);

    @Transactional
    @Query("update User u set u.username =:username where u.id =:id")
    void updateUserNameById(@Param("username") String username, @Param("id") Long id);

}
