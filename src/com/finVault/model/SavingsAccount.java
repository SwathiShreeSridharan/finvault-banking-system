package com.finVault.model;

import com.finVault.enums.AccountStatus;
import com.finVault.enums.AccountType;
import com.finVault.enums.TransactionStatus;
import com.finVault.enums.TransactionType;
import com.finVault.exception.InsufficientFundsException;
import com.finVault.exception.InvalidTransactionException;

public class SavingsAccount extends Account {

    private final double minBalance;
    private final double interestRate;
    private double interestEarned;
    private final int withdrawalLimit;
    private int withdrawalCount;

    public SavingsAccount(Customer customer, double interestRate, int withdrawalLimit,
                          String createdBy, double minBalance, String pin) {
        super(customer, AccountType.SAVINGS, AccountStatus.ACTIVE, pin, createdBy);
        this.minBalance = minBalance;
        this.interestRate = interestRate;
        this.interestEarned = 0.0;
        this.withdrawalLimit = withdrawalLimit;
        this.withdrawalCount = 0;
    }

    @Override
    public void withdraw(double amount) {
        validateAmount(amount);
        validateActiveAccount();
        if (isWithdrawalLimitReached()) {
            throw new InvalidTransactionException("Withdrawal limit exceeded");
        }
        if (getBalance() - amount < minBalance) {
            double shortfall=minBalance-(getBalance()-amount);
            throw new InsufficientFundsException("Balance can not go below minimum balance",shortfall);
        }
        setBalance(getBalance() - amount);
        incrementWithdrawalCount();
        recordTransaction(TransactionType.WITHDRAWAL, amount, getAccountNumber(), null, TransactionStatus.SUCCESS, "Cash Withdrawal");
    }

    private boolean isWithdrawalLimitReached(){
        return withdrawalCount >= withdrawalLimit;
    }
    private void incrementWithdrawalCount(){
        withdrawalCount++;
    }

    @Override
    public double calculateInterest() {
        return getBalance() * interestRate / 100;

    }

    public void applyInterest(){
        double interest = calculateInterest();
        this.interestEarned += interest;
        setBalance(getBalance() + interest);
        recordTransaction(TransactionType.INTEREST,
                interest,
                null,
                getAccountNumber(),
                TransactionStatus.SUCCESS,
                "INTEREST");
    }

    void resetWithdrawalCount() {
        this.withdrawalCount = 0;
    }

    // Getters
    public double getMinBalance() {
        return minBalance;
    }

    public double getInterestRate() {
        return interestRate;
    }

    public double getInterestEarned() {
        return interestEarned;
    }

    public int getWithdrawalLimit() {
        return withdrawalLimit;
    }

    public int getWithdrawalCount() {
        return withdrawalCount;
    }
}
