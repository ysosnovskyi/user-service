package com.solidgate.userservice.controller.v1.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.NotNull;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;

import java.util.Map;

@Validated
@Tag(name = "User API")
public interface UserApi {

    @Operation(summary = "Set user balances")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "User balances successfully updated"),
            @ApiResponse(responseCode = "400", description = "Invalid request params"),
            @ApiResponse(responseCode = "500", description = "Server error")
    })
    ResponseEntity<Void> setUsersBalance(
            @Parameter(description = "Map of user id and new balance") Map<Integer, @NotNull Integer> userBalances);
}
