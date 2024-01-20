package com.baar.springbootjdbctemplate.exception;

public class PersonNotFoundException extends Exception {

    private String message;
    private String throwable;

    public PersonNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public PersonNotFoundException(String message) {
        super(message);
    }

    public PersonNotFoundException(String message, String throwable) {
        this.message = message;
        this.throwable = throwable;
    }
}
