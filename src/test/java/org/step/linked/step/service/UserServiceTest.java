package org.step.linked.step.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.step.linked.step.entity.User;
import org.step.linked.step.exception.UserNotFoundException;
import org.step.linked.step.repository.UserRepository;
import org.step.linked.step.service.impl.UserServiceImpl;
import org.step.linked.step.util.DbHelper;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;
    @Mock
    private PasswordEncoder passwordEncoder;
    @Mock
    private DbHelper<User> userDbHelper;

    @InjectMocks
    private UserServiceImpl userService;

    @Test
    public void saveUser() {
        User user = User.builder()
                .username("username")
                .password("password")
                .age(27)
                .build();

        Mockito.when(passwordEncoder.encode(user.getPassword())).thenReturn("password encoded");
        Mockito.when(userRepository.save(user)).thenReturn(user);
        Mockito.when(userDbHelper.generateId(userRepository)).thenReturn(1L);

        User savedUser = userService.save(user);

        Mockito.verify(passwordEncoder, Mockito.times(1)).encode("password");
        Mockito.verify(userDbHelper, Mockito.times(1)).generateId(userRepository);
        Mockito.verify(userRepository, Mockito.atLeast(1)).save(user);

        Assertions.assertTrue(user.getId() != null && user.getId().equals(1L));
        Assertions.assertEquals("password encoded", user.getPassword());
        Assertions.assertNotNull(savedUser);
    }

    @Test
    public void shouldReturnAllUsers() {
        List<User> userList = Arrays.asList(
                User.builder().id(1L).username("first").password("first").age(20).build(),
                User.builder().id(2L).username("second").password("second").age(25).build()
        );

        Mockito.when(userRepository.findAll()).thenReturn(userList);

        List<User> allUsers = userService.findAll();

        Mockito.verify(userRepository, Mockito.times(1)).findAll();

        Assertions.assertNotNull(allUsers);
        Assertions.assertEquals(2, allUsers.size());
        Assertions.assertEquals(1L, allUsers.get(0).getId());
    }

    @Test
    public void shouldReturnUserById() {
        final long id = 1L;

        final User user = User.builder()
                .id(id)
                .build();

        Mockito.when(userRepository.findById(id)).thenReturn(Optional.of(user));

        User byId = userService.findById(id);

        Mockito.verify(userRepository, Mockito.times(1)).findById(id);

        Assertions.assertNotNull(byId);
        Assertions.assertEquals(id, byId.getId());
    }

    @Test
    public void shouldThrowUserNotFoundException_whenFindById() {
        final long id = 1L;

        Mockito.verify(userRepository, Mockito.times(0)).findById(id);

        Assertions.assertThrows(UserNotFoundException.class, () -> userService.findById(id));
    }
}
