package com.springgoals.exception;

public class AuthenticationException extends Exception {


    private String message;

    @Override
    public String getMessage() {

        return message;
    }

    public AuthenticationException(String message) {
        super(message);
        this.message = message;
    }
}
