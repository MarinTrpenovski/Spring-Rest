package com.springgoals.exception;

public class ValidationsException extends Exception {

    private String message;

    @Override
    public String getMessage() {

        return message;
    }

    public ValidationsException(String message) {
        super(message);
        this.message = message;


    }
}
