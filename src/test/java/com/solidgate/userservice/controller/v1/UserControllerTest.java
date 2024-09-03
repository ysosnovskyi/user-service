package com.solidgate.userservice.controller.v1;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.solidgate.userservice.repository.UserRepository;
import jakarta.validation.ConstraintViolationException;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.Collections;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static com.solidgate.userservice.utils.TestUtils.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Testcontainers
@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class UserControllerTest {

    private static final String BASE_URL = "/api/v1/users";
    private static final String SET_USERS_BALANCE_URL = BASE_URL + "/balance";

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private UserRepository userRepository;

    @Test
    @Transactional
    @SneakyThrows
    void setUsersBalanceShouldReturn204() {
        // Arrange
        var userIdBalanceMap = Map.of(USER_ID1, USER_BALANCE1,
                USER_ID2, USER_BALANCE2, USER_ID4, USER_BALANCE3);

        // Act & Assert
        mockMvc.perform(patch(SET_USERS_BALANCE_URL)
                        .content(objectMapper.writeValueAsString(userIdBalanceMap))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());

        assertThat(userRepository.findAllById(userIdBalanceMap.keySet()))
                .allMatch(user -> userIdBalanceMap.get(user.getId()).equals(user.getBalance()));
    }

    @Test
    @SneakyThrows
    void setUsersBalanceShouldReturn400() {
        var userIdBalanceMap = Collections.singletonMap(USER_ID1, null);

        mockMvc.perform(patch(SET_USERS_BALANCE_URL)
                        .content(objectMapper.writeValueAsString(userIdBalanceMap))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(result -> assertThat(result.getResolvedException())
                        .isInstanceOf(ConstraintViolationException.class)
                        .hasMessageContaining(NULL_BALANCE_ERROR_MESSAGE));
    }

    @Test
    @Transactional
    @SneakyThrows
    void setUsersBalancePerformanceTestShouldReturn204() {
        int startId = 1;
        int endId = 100_000;
        var newBalance = 100_000;
        var userIdBalanceMap = IntStream.rangeClosed(startId, endId).boxed()
                .collect(Collectors.toMap(Function.identity(), i -> newBalance));

        mockMvc.perform(patch(SET_USERS_BALANCE_URL)
                        .content(objectMapper.writeValueAsString(userIdBalanceMap))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());

        assertThat(userRepository.checkAllUsersInRangeHaveBalance(startId, endId, newBalance)).isTrue();
    }
}