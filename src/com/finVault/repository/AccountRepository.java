package com.finVault.repository;

import com.finVault.interfaces.Repository;
import com.finVault.model.Account;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class AccountRepository implements Repository<Account,String> {
    private final Map<String, Account> accountMap=new HashMap<>();

    public void save(Account account){
        accountMap.put(account.getAccountNumber(), account);
    }
    public Account findById(String accountNumber){
        return accountMap.get(accountNumber);
    }
    public Collection<Account> findAll(){
        return Collections.unmodifiableCollection(accountMap.values());
    }
    public void delete(String accountNumber){
        accountMap.remove(accountNumber);
    }
    public boolean exists(String accountNumber){
        return accountMap.containsKey(accountNumber);
    }
}
