package com.solidgate.userservice.exception;

import com.solidgate.userservice.exception.dto.ErrorDTO;
import jakarta.validation.ValidationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.ServletWebRequest;

@RestControllerAdvice
public class ExceptionHandlerAdvice {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({ValidationException.class})
    public ErrorDTO handleValidationException(ValidationException validationException,
                                              ServletWebRequest webRequest) {
        return ErrorDTO.builder()
                .message(validationException.getMessage())
                .requestMethod(webRequest.getHttpMethod().name())
                .build();
    }
}
