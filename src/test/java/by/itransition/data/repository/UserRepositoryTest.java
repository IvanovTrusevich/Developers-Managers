//package by.itransition.data.repository;
//
//import by.itransition.config.JpaConfig;
//import by.itransition.data.model.User;
//import org.fest.assertions.Assertions;
//import org.junit.Before;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.data.domain.Example;
//import org.springframework.test.context.ActiveProfiles;
//import org.springframework.test.context.ContextConfiguration;
//import org.springframework.test.context.TestExecutionListeners;
//import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
//import org.springframework.test.context.support.AnnotationConfigContextLoader;
//import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
//import org.springframework.test.context.support.DirtiesContextTestExecutionListener;
//import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
//import org.springframework.transaction.annotation.Transactional;
//
//import static org.hamcrest.CoreMatchers.is;
//import static org.hamcrest.CoreMatchers.notNullValue;
//import static org.junit.Assert.*;
//
//@ActiveProfiles(profiles = "dev")
//@ContextConfiguration(loader = AnnotationConfigContextLoader.class, classes = {JpaConfig.class})
//@RunWith(SpringJUnit4ClassRunner.class)
//@Transactional
//@TestExecutionListeners({DependencyInjectionTestExecutionListener.class, DirtiesContextTestExecutionListener.class,
//        TransactionalTestExecutionListener.class})
//public class UserRepositoryTest {
//    @Autowired
//    private UserRepository userRepository;
//
//    private final User user1 = new User("ilya@gmail.com", "ilya");
//
//    private final User user2 = new User("valik@gmail.com", "valik", );
//
//    private Long id;
//
//    @Before
//    public void setUp() throws Exception {
//        id = userRepository.save(user1).getId();
//        userRepository.save(user2);
//    }
//
//    @Test
//    public void testFindAll() {
//        final List<UserEntity> all = userRepository.findAll();
//        Assertions.assertThat(all).isNotNull().isNotEmpty();
//        Assertions.assertThat(all).contains(user1, user2);
//    }
//
//    @Test
//    public void testFindOne() {
//        final UserEntity one = userRepository.findOne(id);
//        assertThat("Cannot find record by ID", one.getCredentials(), is("ilya@gmail.com"));
//    }
//
//    @Test
//    public void testSave() {
//        UserEntity user = new UserEntity("ololo", "1234");
//        UserEntity savedUser = userRepository.save(user);
//
//        UserEntity userFromDb = userRepository.findOne(savedUser.getId());
//
//        assertThat(user, is(savedUser));
//        assertThat(userFromDb.getId(), is(user.getId()));
//    }
//
//    @Test
//    public void testDelete() {
//        UserEntity user = new UserEntity("trololo", "12345");
//
//        userRepository.save(user);
//        UserEntity userFromDb = userRepository.findOne(user.getId());
//        userRepository.delete(userFromDb);
//
//        assertNull(userRepository.findOne(userFromDb.getId()));
//    }
//
//    @Test
//    public void testFindByLoginAndPassword() {
//        final UserEntity entity = userRepository.findByEmailAndPassword("ilya@gmail.com", "ilya");
//        assertThat(entity, notNullValue());
//        assertThat(entity.getCredentials(), is("ilya@gmail.com"));
//        assertThat(entity.getPassword(), is("ilya"));
//    }
//
//    @Test
//    public void testCountByLogin() {
//        final boolean ilya = userRepository.exists(Example.of(new UserEntity("ilya@gmail.com", null, null, null)));
//        final boolean nonexistent = userRepository.exists(Example.of(new UserEntity("nonexistent login", null, null, null)));
//        assertThat(ilya, is(true));
//        assertThat(nonexistent, is(false));
//    }
