package com.morev.movies.exception;

public class AccountNotVefied extends Exception {
    public AccountNotVefied() {
        super();
    }
    public AccountNotVefied(String message) {
        super(message);
    }
    public AccountNotVefied(String message, Throwable cause) {
        super(message, cause);
    }
}
