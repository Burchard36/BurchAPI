package com.burchard36.api.command.exceptions;

public class CommandConstructorNotFoundException extends RuntimeException {
    public CommandConstructorNotFoundException(final String msg) {
        super(msg);
    }
}
