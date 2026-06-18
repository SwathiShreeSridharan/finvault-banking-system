package com.finVault.repository;

import com.finVault.interfaces.Repository;
import com.finVault.model.Transaction;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class TransactionRepository implements Repository<Transaction,String> {
    private final Map<String, Transaction> transactions=new HashMap<>();

    public void save(Transaction transaction){
        transactions.put(transaction.getTransactionId(), transaction);
    }
    public Transaction findById(String transactionId){
        return transactions.get(transactionId);
    }
    public Collection<Transaction> findAll(){
        return Collections.unmodifiableCollection(transactions.values());
    }
    public void delete(String transactionId){
        transactions.remove(transactionId);
    }
    public boolean exists(String transactionId){
        return transactions.containsKey(transactionId);
    }
}
