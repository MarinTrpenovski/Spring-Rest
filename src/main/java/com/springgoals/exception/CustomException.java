package com.springgoals.exception;

public class CustomException extends Exception {
    private String message;

    @Override
    public String getMessage() {
        return message;
    }

    public CustomException(String message) {
        super(message);
        this.message = message;


    }
}
