package com.parqueadero.billing.exceptions;

public class AlreadyClientExistException extends RuntimeException{
    /**
     *
     */
    private static final long serialVersionUID = -4357758140384890633L;

    public AlreadyClientExistException(String message) {
        super(message);
    }
}

