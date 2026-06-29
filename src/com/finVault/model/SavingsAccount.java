package com.finVault.model;

import com.finVault.enums.AccountStatus;
import com.finVault.enums.AccountType;
import com.finVault.enums.TransactionType;
import com.finVault.exception.InsufficientFundsException;
import com.finVault.exception.InvalidTransactionException;

import java.math.BigDecimal;

public class SavingsAccount extends Account {

    private final BigDecimal minBalance;
    private final BigDecimal interestRate;
    private BigDecimal interestEarned;
    private final int withdrawalLimit;
    private int withdrawalCount;

    public SavingsAccount(Customer customer, BigDecimal interestRate, int withdrawalLimit,
                          String createdBy, BigDecimal minBalance, String pin) {
        super(customer, AccountType.SAVINGS, AccountStatus.ACTIVE, pin, createdBy);
        this.minBalance = minBalance;
        this.interestRate = interestRate;
        this.interestEarned = BigDecimal.ZERO;
        this.withdrawalLimit = withdrawalLimit;
        this.withdrawalCount = 0;
    }

    @Override
    public void withdraw(BigDecimal amount) {
        validateAmount(amount);
        validateActiveAccount();
        if (isWithdrawalLimitReached()) {
            throw new InvalidTransactionException("Withdrawal limit exceeded");
        }
        if (getBalance().subtract(amount).compareTo(minBalance)<0) {
            BigDecimal shortfall=minBalance.subtract(getBalance().subtract(amount));
            throw new InsufficientFundsException("Balance can not go below minimum balance",shortfall);
        }
        setBalance(getBalance().subtract(amount));
        incrementWithdrawalCount();
        recordTransaction(TransactionType.WITHDRAWAL, amount, getAccountNumber(), null, "Cash Withdrawal");
    }

    private boolean isWithdrawalLimitReached(){
        return withdrawalCount >= withdrawalLimit;
    }
    private void incrementWithdrawalCount(){
        withdrawalCount++;
    }

    @Override
    public BigDecimal calculateInterest() {
        return getBalance().multiply(interestRate.divide(BigDecimal.valueOf(100)));

    }

    public void applyInterest(){
        BigDecimal interest = calculateInterest();
        this.interestEarned = interestEarned.add(interest);
        setBalance(getBalance().add(interest));
        recordTransaction(TransactionType.INTEREST,
                interest,
                null,
                getAccountNumber(),
                "INTEREST");
    }

    void resetWithdrawalCount() {
        this.withdrawalCount = 0;
    }

    // Getters
    public BigDecimal getMinBalance() {
        return minBalance;
    }

    public BigDecimal getInterestRate() {
        return interestRate;
    }

    public BigDecimal getInterestEarned() {
        return interestEarned;
    }

    public int getWithdrawalLimit() {
        return withdrawalLimit;
    }

    public int getWithdrawalCount() {
        return withdrawalCount;
    }
}
