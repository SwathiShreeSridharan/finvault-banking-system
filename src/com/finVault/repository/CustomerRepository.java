package com.finVault.repository;

import com.finVault.interfaces.Repository;
import com.finVault.model.Customer;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class CustomerRepository implements Repository<Customer,String> {
    private final Map<String, Customer> customerMap=new HashMap<>();
    public void save(Customer customer){
        customerMap.put(customer.getCustomerId(), customer);
    }
    public Customer findById(String customerId){
        return customerMap.get(customerId);
    }
    public Collection<Customer> findAll(){
        return Collections.unmodifiableCollection(customerMap.values());
    }
    public void update(Customer customer){
        customerMap.put(customer.getCustomerId(), customer);
    }
    public void delete(String customerId){
        customerMap.remove(customerId);
    }
    public boolean exists(String customerId){
        return customerMap.containsKey(customerId);
    }
}
