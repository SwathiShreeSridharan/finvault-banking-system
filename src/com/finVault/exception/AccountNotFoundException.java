package com.finVault.exception;

public class AccountNotFoundException extends FinVaultException {
    public AccountNotFoundException(String message) {
        super(message);
    }
}
