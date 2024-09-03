package com.solidgate.userservice.exception.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ErrorDTO {
    private final String message;
    private final String requestMethod;
}
