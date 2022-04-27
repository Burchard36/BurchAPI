package com.burchard36.api.command.exceptions;

public class CommandInvocationException extends RuntimeException{
    protected CommandInvocationException(String msg) {
        super(msg);
    }
}
