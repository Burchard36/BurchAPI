package com.burchard36.api.command.exceptions;

public class CommandExceptionFactory {


    protected static CommandConstructorNotFoundException newConstructorNotFoundException(String msg) {
        return new CommandConstructorNotFoundException(msg);
    }

    protected static InvalidCommandAnnotationException newInvalidCommandAnnotationException(String msg) {
        return new InvalidCommandAnnotationException(msg);
    }

    protected static ClassNotTypeOfApiCommandException newClassNotTypeOfApiCommand(String msg) {
        return new ClassNotTypeOfApiCommandException(msg);
    }

    protected static CommandInvocationException newCommandInvocationException(String msg) {
        return new CommandInvocationException(msg);
    }
}
