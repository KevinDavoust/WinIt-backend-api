package com.cda.winit.auth.infrastructure.repository.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
@RestControllerAdvice
public class RegistrationErrorAdvice {
    @ExceptionHandler(RegistrationErrorException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    String errorRegistrationHandler(RegistrationErrorException ex) {
        return ex.getMessage();
    }
}
