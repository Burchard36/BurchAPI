package com.burchard36.api.command.exceptions;

public class ClassNotTypeOfApiCommandException extends RuntimeException {
    protected ClassNotTypeOfApiCommandException(String msg) {
        super(msg);
    }
}
