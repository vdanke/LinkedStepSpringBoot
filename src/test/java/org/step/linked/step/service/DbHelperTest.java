package org.step.linked.step.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.step.linked.step.entity.User;
import org.step.linked.step.repository.UserRepository;
import org.step.linked.step.util.DbHelper;

@ExtendWith(MockitoExtension.class)
public class DbHelperTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private DbHelper<User> dbHelper;

    @Test
    public void shouldIncrementId() {
        Mockito.when(userRepository.findTopByOrderByIdDesc()).thenReturn(() -> 1L);

        Long aLong = dbHelper.generateId(userRepository);

        Assertions.assertNotEquals(1L, aLong);
        Assertions.assertEquals(2L, aLong);
    }
}
