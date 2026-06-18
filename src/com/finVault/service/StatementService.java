package com.finVault.service;

import com.finVault.model.Account;
import com.finVault.model.Transaction;
import com.finVault.repository.AccountRepository;
import com.finVault.repository.TransactionRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static com.finVault.view.StatementPrinter.printStatement;

public class StatementService {
    private static final int miniStatementSize = 10;
    AccountRepository accountRepository;
    public StatementService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }
    public List<Transaction> getMiniStatement(String accountNumber) {
        Account account = accountRepository.findById(accountNumber);
        List<Transaction> transactions=account.getTransactionHistory();
        int size = transactions.size();
        return account.getTransactionHistory().subList(Math.max(0, size-miniStatementSize),size);
    }

    public List<Transaction> getFullStatement(String accountNumber) {
        Account account = accountRepository.findById(accountNumber);
        List<Transaction> transactions=account.getTransactionHistory();
        return account.getTransactionHistory();
    }

    public List<Transaction> getStatementByDate(String accountNumber, LocalDate startDate, LocalDate endDate) {
        Account account = accountRepository.findById(accountNumber);
        List<Transaction> transactions=account.getTransactionHistory();
        return transactions.stream().
                filter(t->!t.getTransactionDateTime().toLocalDate().isBefore(startDate)
                        && !t.getTransactionDateTime().toLocalDate().isAfter(endDate)).
                sorted(Comparator.comparing(Transaction::getTransactionDateTime)).
                collect(Collectors.toList());
    }
}
