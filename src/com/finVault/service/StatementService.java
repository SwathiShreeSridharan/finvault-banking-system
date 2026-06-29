package com.finVault.service;

import com.finVault.exception.AccountNotFoundException;
import com.finVault.model.Account;
import com.finVault.model.Transaction;
import com.finVault.repository.AccountRepository;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;


public class StatementService {
    private static final int miniStatementSize = 10;
    private final AccountRepository accountRepository;
    public StatementService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }
    public List<Transaction> getMiniStatement(String accountNumber) {
        Account account = accountRepository.findById(accountNumber);
        if(account == null){
            throw new AccountNotFoundException("Account not found");
        }
        List<Transaction> transactions=account.getTransactionHistory();
        int size = transactions.size();
        return account.getTransactionHistory().subList(Math.max(0, size-miniStatementSize),size);
    }

    public List<Transaction> getFullStatement(String accountNumber) {
        Account account = accountRepository.findById(accountNumber);
        if(account == null){
            throw new AccountNotFoundException("Account not found");
        }
        return account.getTransactionHistory();
    }

    public List<Transaction> getStatementByDate(String accountNumber, LocalDate startDate, LocalDate endDate) {
        Account account = accountRepository.findById(accountNumber);
        if(account == null){
            throw new AccountNotFoundException("Account not found");
        }
        List<Transaction> transactions=account.getTransactionHistory();
        return transactions.stream().
                filter(t->!t.getTransactionDateTime().toLocalDate().isBefore(startDate)
                        && !t.getTransactionDateTime().toLocalDate().isAfter(endDate)).
                sorted(Comparator.comparing(Transaction::getTransactionDateTime)).
                collect(Collectors.toList());
    }
}
