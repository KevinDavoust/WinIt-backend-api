package com.cda.winit.team.repository.exception;

public class TeamNameAlreadyExistsException extends RuntimeException {
        public TeamNameAlreadyExistsException(String message) {
            super(message);
        }
}
