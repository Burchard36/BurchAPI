package com.burchard36.command.exceptions;

public class CommandInterfaceNotFoundException extends RuntimeException {
    public CommandInterfaceNotFoundException(final String msg) {
        super(msg);
    }
}
