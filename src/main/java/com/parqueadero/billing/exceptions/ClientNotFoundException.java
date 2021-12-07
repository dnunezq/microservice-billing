package com.parqueadero.billing.exceptions;

public class ClientNotFoundException extends RuntimeException {
    /**
     *
     */
    private static final long serialVersionUID = -3596598208436862786L;

    public ClientNotFoundException(String message) {
        super(message);
    }
}
