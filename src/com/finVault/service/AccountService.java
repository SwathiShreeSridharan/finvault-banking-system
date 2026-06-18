package com.finVault.service;

import com.finVault.exception.AccountAlreadyExistsException;
import com.finVault.exception.AccountNotFoundException;
import com.finVault.exception.InvalidTransactionException;
import com.finVault.model.Account;
import com.finVault.repository.AccountRepository;

import java.util.Collection;
import java.util.Collections;

public class AccountService {
    private final AccountRepository accountRepository;

    public AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public void createAccount(Account account){
        if(accountRepository.exists(account.getAccountNumber())){
            throw new AccountAlreadyExistsException("Account already exists");
        }
        accountRepository.save(account);
        account.getCustomer().addAccount(account.getAccountNumber());
    }

    public Account findAccount(String accountNumber){
        Account account=accountRepository.findById(accountNumber);
        if(account==null){
            throw new AccountNotFoundException("Account not found");
        }
        return account;
    }

    public Collection<Account> findAllAccounts(){
        return Collections.unmodifiableCollection(accountRepository.findAll());
    }

    public void closeAccount(String accountNumber){
        Account account=findAccount(accountNumber);
        account.closeAccount();
    }

    public void freezeAccount(String accountNumber){
        Account account=findAccount(accountNumber);
        account.freezeAccount();
    }

    public double getBalance(String accountNumber){
        Account account=findAccount(accountNumber);
        return account.getBalance();
    }

    public void changePin(String accountNumber, String oldPin, String newPin){
        Account account=findAccount(accountNumber);
        if(!account.isValidPin(oldPin)){
            throw new InvalidTransactionException("Invalid old pin");
        }
        if(oldPin.equals(newPin)){
            throw new InvalidTransactionException("New pin cannot be the same as the old pin");
        }
        account.setPin(newPin);
    }
}
