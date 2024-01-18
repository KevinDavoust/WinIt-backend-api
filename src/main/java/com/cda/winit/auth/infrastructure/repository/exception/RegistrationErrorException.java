package com.cda.winit.auth.infrastructure.repository.exception;

public class RegistrationErrorException extends RuntimeException{
    public RegistrationErrorException(String message) {
        super(message);
    }
}
