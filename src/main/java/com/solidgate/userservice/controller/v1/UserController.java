package com.solidgate.userservice.controller.v1;

import com.solidgate.userservice.controller.v1.api.UserApi;
import com.solidgate.userservice.service.UserService;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/users")
public class UserController implements UserApi {

    private final UserService userService;

    @PatchMapping("/balance")
    public ResponseEntity<Void> setUsersBalance(@RequestBody Map<Integer, @NotNull Integer> userBalances) {
        userService.setUsersBalance(userBalances);
        return ResponseEntity.noContent().build();
    }
}
