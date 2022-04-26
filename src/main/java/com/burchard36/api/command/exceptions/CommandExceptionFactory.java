package com.burchard36.api.command.exceptions;

public class CommandExceptionFactory {

    protected static CommandConstructorNotFoundException newConstructorNotFoundException(String msg) {
        return new CommandConstructorNotFoundException(msg);
    }

    protected static InvalidCommandAnnotationException newInvalidCommandAnnotationException(String msg) {
        return new InvalidCommandAnnotationException(msg);
    }
}
