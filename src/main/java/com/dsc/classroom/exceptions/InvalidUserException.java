package com.dsc.classroom.exceptions;

public class InvalidUserException extends Exception {
    public InvalidUserException() {
    }

    public InvalidUserException(String message) {
        super(message);
    }
}

