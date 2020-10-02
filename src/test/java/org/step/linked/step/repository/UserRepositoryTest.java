package org.step.linked.step.repository;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.step.linked.step.entity.User;

import java.util.List;
import java.util.Optional;

/*
@DataJpaTest - мы загружаем только слой базы данных
а так же конфигурируем InMemory DB
Используется H2 так как зависимость находится в classpath
 */
@ExtendWith(SpringExtension.class)
@DataJpaTest
@TestPropertySource(locations = {"classpath:application-test.properties"})
@Sql(value = {"classpath:db/test/users/init_user_test.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(value = {"classpath:db/test/users/delete_user_test.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
public class UserRepositoryTest {

    private static final Long USER_ID_TEST = 1L;
    private static final Integer USER_AGE_TEST = 25;
    private static final String USER_DATA_TEST = "fourth";

    @Autowired
    private UserRepository userRepository;

    @Test
    public void shouldReturnAllUsers() {
        List<User> users = userRepository.findAll();

        Assertions.assertNotNull(users);
        Assertions.assertFalse(users.isEmpty());
    }

    @Test
    public void shouldReturnUserById() {
        Optional<User> userById = userRepository.findById(USER_ID_TEST);

        Assertions.assertTrue(userById.isPresent());
        Assertions.assertEquals(USER_ID_TEST, userById.get().getId());
    }

    @Test
    public void shouldSaveUser() {
        final long id = 99L;
        final User user = User.builder()
                .id(id)
                .username(USER_DATA_TEST)
                .password(USER_DATA_TEST)
                .age(USER_AGE_TEST)
                .build();

        userRepository.saveAndFlush(user);

        Optional<User> byId = userRepository.findById(id);

        Assertions.assertTrue(byId.isPresent());
        Assertions.assertEquals(id, byId.get().getId());
    }

    @Test
    public void shouldDeleteUserById() {
        userRepository.deleteById(USER_ID_TEST);

        userRepository.flush();

        Optional<User> byId = userRepository.findById(USER_ID_TEST);

        Assertions.assertFalse(byId.isPresent());
    }

    @Test
    public void shouldDeleteUser() {
        final User user = User.builder()
                .id(USER_ID_TEST)
                .username(USER_DATA_TEST)
                .password(USER_DATA_TEST)
                .age(USER_AGE_TEST)
                .build();

        userRepository.delete(user);

        userRepository.flush();

        Optional<User> byId = userRepository.findById(USER_ID_TEST);

        Assertions.assertFalse(byId.isPresent());
    }
}
