package com.ziyad.recruitingspring.exceptions;

public class DatabaseAccessException extends RuntimeException {
    public DatabaseAccessException() {
        super();
    }

    public DatabaseAccessException(String message) {
        super(message);
    }

    public DatabaseAccessException(String message, Throwable cause) {
        super(message, cause);
    }
}
