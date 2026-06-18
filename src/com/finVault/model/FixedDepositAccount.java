package com.finVault.model;

import com.finVault.enums.AccountStatus;
import com.finVault.enums.AccountType;
import com.finVault.enums.TransactionStatus;
import com.finVault.enums.TransactionType;
import com.finVault.exception.InvalidTransactionException;

import java.time.LocalDate;

public class FixedDepositAccount extends Account {
    private final double principalAmount;
    private final double interestRate;
    private final LocalDate startDate;
    private final LocalDate matureDate;
    private final int tenureInMonths;
    private final int penaltyRate;

    public FixedDepositAccount(Customer customer, String pin, String createdBy, double principalAmount, double interestRate,
                               int tenureInMonths, int penaltyRate, LocalDate startDate) {
        super(customer, AccountType.FIXED_DEPOSIT, AccountStatus.ACTIVE, pin, createdBy);
        setBalance(principalAmount);
        this.principalAmount = principalAmount;
        this.interestRate = interestRate;
        this.startDate = startDate;
        this.matureDate = startDate.plusMonths(tenureInMonths);
        this.tenureInMonths = tenureInMonths;
        this.penaltyRate = penaltyRate;
    }

    public double getMaturityAmount() {
        return principalAmount+calculateInterest();
    }

    @Override
    public void deposit(double amount) {
        throw new InvalidTransactionException("Fixed Deposit Account do not support deposit amount");
    }

    @Override
    public void withdraw(double amount) {
        validateActiveAccount();
        double withdrawalAmount=isMatured()?getMaturityAmount():principalAmount-amount;
        String description = isMatured() ? "FD_MATURITY_WITHDRAWAL" : "FD_PREMATURE_WITHDRAWAL";
        recordTransaction(
                TransactionType.WITHDRAWAL, withdrawalAmount, this.getAccountNumber(), null, TransactionStatus.SUCCESS, description);
        setBalance(0);
        closeAccount();
    }

    private double getPenaltyAmount() {
        return principalAmount*((double) penaltyRate /100);
    }

    public boolean isMatured() {
        return !LocalDate.now().isBefore(matureDate);
    }

    @Override
    public double calculateInterest() {
        double years=tenureInMonths/12.0;
        return (principalAmount*years*interestRate/100);
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public LocalDate getMatureDate() {
        return matureDate;
    }

    public int getTenureInMonths() {
        return tenureInMonths;
    }

    public double getInterestRate() {
        return interestRate;
    }

    public double getPenaltyRate() {
        return penaltyRate;
    }
}
