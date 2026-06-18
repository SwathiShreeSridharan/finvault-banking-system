package com.finVault.exception;

public class AccountAlreadyExistsException extends FinVaultException {
    public AccountAlreadyExistsException(String message) {
        super(message);
    }
}
