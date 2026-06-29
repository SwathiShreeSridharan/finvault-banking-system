package com.finVault.model;

import com.finVault.enums.AccountStatus;
import com.finVault.enums.AccountType;
import com.finVault.enums.TransactionType;
import com.finVault.exception.InsufficientFundsException;

import java.math.BigDecimal;

public class CurrentAccount extends Account {
    private final BigDecimal overdraftLimit;
    private final BigDecimal transactionFee;

    public CurrentAccount(Customer customerName,String pin, String createdBy, BigDecimal overdraftLimit, BigDecimal transactionFee) {
        super(customerName, AccountType.CURRENT, AccountStatus.ACTIVE, pin, createdBy);
        this.overdraftLimit=overdraftLimit;
        this.transactionFee=transactionFee;
    }

    @Override
    public void withdraw(BigDecimal amount) {
        validateAmount(amount);
        validateActiveAccount();
        BigDecimal newBalance=getBalance().subtract(amount).subtract(transactionFee);
        if(newBalance.compareTo(overdraftLimit.negate())<0){
            BigDecimal shortfall=newBalance.abs().subtract(overdraftLimit);
            throw new InsufficientFundsException("Balance can not go below overdraft limit",shortfall);
        }

        setBalance(newBalance);
        recordTransaction(TransactionType.WITHDRAWAL,amount,
        getAccountNumber(),null,
                "Cash Withdrawal");
    }

    @Override
    public BigDecimal calculateInterest() {
        return BigDecimal.ZERO;
    }

    public BigDecimal getOverdraftLimit() { return overdraftLimit; }
    public BigDecimal getOverdraftUsed() {
        return getBalance().compareTo(BigDecimal.ZERO)<0?getBalance().abs():BigDecimal.ZERO;
    }
    public BigDecimal getTransactionFee() { return transactionFee; }
}
