package com.springgoals.exception;

public class EmailExistsException  extends Exception{

    private String message;

    @Override
    public String getMessage() {

        return message;
    }

    public EmailExistsException(String message) {
        super(message);
        this.message = message;
    }
}
