package com.springgoals.exception;

public class ExpiryException extends Exception {


    private String message;

    @Override
    public String getMessage() {

        return message;
    }

    public ExpiryException(String message) {
        super(message);
        this.message = message;
    }
}
