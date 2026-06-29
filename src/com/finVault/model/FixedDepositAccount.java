package com.finVault.model;

import com.finVault.enums.AccountStatus;
import com.finVault.enums.AccountType;
import com.finVault.enums.TransactionType;
import com.finVault.exception.InvalidTransactionException;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;


public class FixedDepositAccount extends Account {
    private final BigDecimal principalAmount;
    private final BigDecimal interestRate;
    private final LocalDate startDate;
    private final LocalDate matureDate;
    private final int tenureInMonths;
    private final BigDecimal penaltyRate;

    public FixedDepositAccount(Customer customer, String pin, String createdBy, BigDecimal principalAmount, BigDecimal interestRate,
                               int tenureInMonths, BigDecimal penaltyRate, LocalDate startDate) {
        super(customer, AccountType.FIXED_DEPOSIT, AccountStatus.ACTIVE, pin, createdBy);
        setBalance(principalAmount);
        this.principalAmount = principalAmount;
        this.interestRate = interestRate;
        this.startDate = startDate;
        this.matureDate = startDate.plusMonths(tenureInMonths);
        this.tenureInMonths = tenureInMonths;
        this.penaltyRate = penaltyRate;
    }

    public BigDecimal getMaturityAmount() {
        return principalAmount.add(calculateInterest());
    }

    @Override
    public void deposit(BigDecimal amount) {
        throw new InvalidTransactionException("Fixed Deposit Account do not support deposit amount");
    }

    @Override
    public void withdraw(BigDecimal amount) {
        validateActiveAccount();
        BigDecimal withdrawalAmount=isMatured()?getMaturityAmount():principalAmount.subtract(getPenaltyAmount());
        String description = isMatured() ? "FD_MATURITY_WITHDRAWAL" : "FD_PREMATURE_WITHDRAWAL";
        recordTransaction(
                TransactionType.WITHDRAWAL, withdrawalAmount, this.getAccountNumber(), null, description);
        setBalance(BigDecimal.ZERO);
        closeAccount();
    }

    private BigDecimal getPenaltyAmount() {
        return principalAmount.multiply(penaltyRate.divide(BigDecimal.valueOf(100),2, RoundingMode.HALF_UP));
    }

    public boolean isMatured() {
        return !LocalDate.now().isBefore(matureDate);
    }

    @Override
    public BigDecimal calculateInterest() {
        BigDecimal years = BigDecimal.valueOf(tenureInMonths)
                .divide(BigDecimal.valueOf(12), 2, RoundingMode.HALF_UP);

        return principalAmount
                .multiply(years)
                .multiply(interestRate)
                .divide(BigDecimal.valueOf(100), 2, RoundingMode.HALF_UP);
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

    public BigDecimal getInterestRate() {
        return interestRate;
    }

    public BigDecimal getPenaltyRate() {
        return penaltyRate;
    }
}
