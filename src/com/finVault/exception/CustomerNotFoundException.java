package com.finVault.exception;

public class CustomerNotFoundException extends FinVaultException {
    public CustomerNotFoundException(String message) {
        super(message);
    }
}
