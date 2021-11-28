package com.parqueadero.billing.exceptions;

public class AlreadyClientExistException extends RuntimeException{
    public AlreadyClientExistException(String message) {
        super(message);
    }
}

