package com.burchard36.api.command.exceptions;

public class InvalidCommandAnnotationException extends RuntimeException {
    public InvalidCommandAnnotationException(final String msg) {
        super(msg);
    }
}
