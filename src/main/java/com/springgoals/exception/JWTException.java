package com.springgoals.exception;

public class JWTException extends Exception {


    private String message;

    @Override
    public String getMessage() {

        return message;
    }

    public JWTException(String message) {
        super(message);
        this.message = message;
    }
}
