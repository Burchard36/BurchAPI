package com.burchard36.api.command.exceptions;

public class CommandInterfaceNotFoundException extends RuntimeException {
    public CommandInterfaceNotFoundException(final String msg) {
        super(msg);
    }
}
