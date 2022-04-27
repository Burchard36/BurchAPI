package com.burchard36.api.command.exceptions;

public class ClassNotTypeOfApiCommand extends RuntimeException {
    protected ClassNotTypeOfApiCommand(String msg) {
        super(msg);
    }
}
