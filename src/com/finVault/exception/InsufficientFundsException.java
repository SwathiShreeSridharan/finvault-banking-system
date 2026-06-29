package com.finVault.exception;


import java.math.BigDecimal;
import java.util.function.BiConsumer;

public class InsufficientFundsException extends FinVaultException {
    private final BigDecimal shortfallAmount;
    public InsufficientFundsException(String message, BigDecimal shortfallAmount) {
        super(message);
        this.shortfallAmount = shortfallAmount;
    }

    public BigDecimal getShortfallAmount() {
        return shortfallAmount;
    }
}
