package com.ziyad.recruitingspring.exceptions;

public class DuplicateJobException extends RuntimeException {

    public DuplicateJobException() {
        super();
    }

    public DuplicateJobException(String message) {
        super(message);
    }

    public DuplicateJobException(String message, Throwable cause) {
        super(message, cause);
    }
}