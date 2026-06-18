package com.finVault.exception;

public class CustomerAlreadyExistsException extends FinVaultException {
    public CustomerAlreadyExistsException(String message) {
        super(message);
    }
}
