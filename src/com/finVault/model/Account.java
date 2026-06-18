package com.finVault.model;

import com.finVault.entity.BaseEntity;
import com.finVault.enums.AccountStatus;
import com.finVault.enums.AccountType;
import com.finVault.enums.TransactionStatus;
import com.finVault.enums.TransactionType;
import com.finVault.exception.InvalidTransactionException;
import com.finVault.utils.IDGenerator;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public abstract class Account extends BaseEntity {
    private final String accountNumber;
    private final Customer customer;
    private final AccountType accountType;
    private AccountStatus accountStatus;
    private final LocalDate createdDate;
    private double balance;
    private String pin;
    private final List<Transaction> transactions;


    public Account(Customer customer, AccountType accountType,
                   AccountStatus accountStatus, String pin, String createdBy) {
        this.accountNumber = IDGenerator.generateAccountNumber();
        this.customer = customer;
        this.accountType = accountType;
        this.accountStatus = accountStatus;
        this.createdDate = LocalDate.now();
        this.balance = 0.0;
        this.pin = pin;
        this.transactions = new ArrayList<>();
        this.createdBy=createdBy;
        this.createdAt=LocalDateTime.now();
    }


    protected void setBalance(double balance) {
        this.balance = balance;
    }

    // Concrete methods
    public void deposit(double amount) {
        validateAmount(amount);
        validateActiveAccount();
        setBalance(getBalance() + amount);
        recordTransaction(
                TransactionType.DEPOSIT,
                amount,
                null,
                getAccountNumber(),
                TransactionStatus.SUCCESS,
                "DEPOSIT");
    }

    public boolean isActive() {
        return getAccountStatus() == AccountStatus.ACTIVE;
    }

    public boolean isValidPin(String pin) {
        return this.pin.equals(pin);
    }

    protected void addTransaction(Transaction transaction) {
        this.transactions.add(transaction);
    }
    public void freezeAccount() {
        setAccountStatus(AccountStatus.FROZEN);
        this.updatedAt = LocalDateTime.now();
        this.updatedBy="SYSTEM";
    }

    public void closeAccount() {
        setAccountStatus(AccountStatus.CLOSED);
        this.updatedAt = LocalDateTime.now();
        this.updatedBy="SYSTEM";
    }

    protected void setAccountStatus(AccountStatus accountStatus) {
        this.accountStatus = accountStatus;
    }

    protected void validateAmount(double amount) {
        if (amount <= 0) {
            throw new InvalidTransactionException("Amount must be greater than 0");
        }
    }

    protected void validateActiveAccount() {
        if(!isActive()) {
            throw new InvalidTransactionException(
                    "Account is not active");
        }
    }

    public String getAccountNumber() { return accountNumber; }
    public Customer getCustomer() { return customer; }
    public double getBalance() { return balance; }
    public AccountType getAccountType() { return accountType; }
    public AccountStatus getAccountStatus() { return accountStatus; }
    public LocalDate getCreatedDate() { return createdDate; }
    public List<Transaction> getTransactionHistory() { return Collections.unmodifiableList(transactions); }

    public void setPin(String newPin) {
        this.pin = newPin;
        this.updatedAt = LocalDateTime.now();
        this.updatedBy="SYSTEM";
    }

    public abstract void withdraw(double amount);
    public abstract double calculateInterest();

    protected void recordTransaction(TransactionType type,
                                  double amount,
                                  String fromAccount,
                                  String toAccount,
                                  TransactionStatus status,
                                  String description) {
        Transaction t = new Transaction(
                type, amount, fromAccount, toAccount,
                createdBy,
                getBalance(), status,description);
        addTransaction(t);
    }

}

