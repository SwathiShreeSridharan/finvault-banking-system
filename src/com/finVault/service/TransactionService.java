package com.finVault.service;

import com.finVault.exception.InvalidTransactionException;
import com.finVault.model.Account;
import com.finVault.model.Transaction;
import com.finVault.repository.TransactionRepository;

import java.math.BigDecimal;
import java.util.List;

public class TransactionService {
    private final TransactionRepository transactionRepository;
    private final AccountService accountService;
    public TransactionService(TransactionRepository transactionRepository, AccountService accountService) {
        this.transactionRepository = transactionRepository;
        this.accountService = accountService;
    }

    public void deposit(String accountNumber, BigDecimal amount) {
        Account account = accountService.findAccount(accountNumber);
        account.deposit(amount);
        saveLatestTransaction(account);
    }

    public void withdraw(String accountNumber, BigDecimal amount) {
        Account account = accountService.findAccount(accountNumber);
        account.withdraw(amount);
        saveLatestTransaction(account);
    }

    public void transfer(String fromAccountNumber, String toAccountNumber, BigDecimal amount) {
        if(fromAccountNumber.equals(toAccountNumber)) {
            throw new InvalidTransactionException("From account number cannot be the same as to account number");
        }
        Account fromAccount = accountService.findAccount(fromAccountNumber);
        Account toAccount = accountService.findAccount(toAccountNumber);
        fromAccount.withdraw(amount);
        toAccount.deposit(amount);
        saveLatestTransaction(fromAccount);
        saveLatestTransaction(toAccount);
    }

    public void saveLatestTransaction(Account account) {
        List<Transaction> history=account.getTransactionHistory();
        if(!history.isEmpty()) {
            transactionRepository.save(history.get(history.size()-1));
        }
    }
}
