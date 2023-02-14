package com.springgoals.exception;

public class QueryException extends Exception {

        private String message;

        @Override
        public String getMessage() {
            return message;
        }

    QueryException(){}
    public QueryException(String message) {
        super(message);
        this.message = message;
        System.out.println(message);
    }

}
