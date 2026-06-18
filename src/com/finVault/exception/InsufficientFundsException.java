package com.finVault.exception;


public class InsufficientFundsException extends FinVaultException {
    private final double shortfallAmount;
    public InsufficientFundsException(String message, double shortfallAmount) {
        super(message);
        this.shortfallAmount = shortfallAmount;
    }

    public  double getShortfallAmount() {
        return shortfallAmount;
    }
}
