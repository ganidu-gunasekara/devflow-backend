package com.devflow.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class AppException {

    public static class NotFoundException extends ResponseStatusException {
        public NotFoundException(String message) {
            super(HttpStatus.NOT_FOUND, message);
        }
    }

    public static class ConflictException extends ResponseStatusException {
        public ConflictException(String message) {
            super(HttpStatus.CONFLICT, message);
        }
    }

    public static class BadRequestException extends ResponseStatusException {
        public BadRequestException(String message) {
            super(HttpStatus.BAD_REQUEST, message);
        }
    }

    public static class ForbiddenException extends ResponseStatusException {
        public ForbiddenException(String message) {
            super(HttpStatus.FORBIDDEN, message);
        }
    }
}