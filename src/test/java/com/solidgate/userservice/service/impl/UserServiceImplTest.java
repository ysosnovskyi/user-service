package com.solidgate.userservice.service.impl;

import com.solidgate.userservice.config.properties.BatchProperties;
import com.solidgate.userservice.repository.UserRepository;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static com.solidgate.userservice.utils.TestUtils.*;
import static java.util.Objects.nonNull;
import static org.apache.commons.lang3.math.NumberUtils.INTEGER_TWO;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @Mock
    private BatchProperties batchProperties;
    @Mock
    private UserRepository userRepository;
    @Mock
    private EntityManager entityManager;

    @InjectMocks
    private UserServiceImpl userService;

    @Test
    void shouldSetUsersBalance() {
        var inputMap = Map.of(USER_ID1, USER_BALANCE1,
                USER_ID2, USER_BALANCE2, USER_ID3, USER_BALANCE3);
        var user = buildUser();

        when(batchProperties.getSize()).thenReturn(BATCH_SIZE);
        when(userRepository.findAllById(argThat((Set<Integer> set) -> nonNull(set) && !set.contains(USER_ID1))))
                .thenReturn(List.of());
        when(userRepository.findAllById(argThat((Set<Integer> set) -> nonNull(set) && set.contains(USER_ID1))))
                .thenReturn(List.of(user));

        userService.setUsersBalance(inputMap);

        assertThat(user.getBalance()).isEqualTo(USER_BALANCE1);
        verify(batchProperties, only()).getSize();
        verify(userRepository, times(INTEGER_TWO)).findAllById(anySet());
        verify(userRepository, times(INTEGER_TWO)).saveAllAndFlush(anyList());
        verify(entityManager, times(INTEGER_TWO)).clear();
    }

    @Test
    void shouldThrowExceptionWhenSetUsersBalance() {
        var inputMap = Collections.<Integer, Integer>singletonMap(null, USER_BALANCE1);

        when(batchProperties.getSize()).thenReturn(BATCH_SIZE);
        when(userRepository.findAllById(ArgumentMatchers.<Set<Integer>>any()))
                .thenThrow(IllegalArgumentException.class);

        assertThatThrownBy(() -> userService.setUsersBalance(inputMap))
                .isExactlyInstanceOf(IllegalArgumentException.class);

        verify(batchProperties, only()).getSize();
        verify(userRepository, only()).findAllById(anySet());
        verifyNoInteractions(entityManager);
    }

}