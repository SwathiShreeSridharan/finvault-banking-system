package com.finVault.service;

import com.finVault.enums.CustomerStatus;
import com.finVault.enums.KycStatus;
import com.finVault.exception.CustomerAlreadyExistsException;
import com.finVault.exception.CustomerNotFoundException;
import com.finVault.model.Customer;
import com.finVault.repository.CustomerRepository;

import java.util.Collection;
import java.util.Collections;

public class CustomerService {
    private final CustomerRepository customerRepository;
    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }
    public void registerCustomer(Customer customer){
        if(customerRepository.exists(customer.getCustomerId())){
            throw new CustomerAlreadyExistsException("Customer already exists");
        }
        customerRepository.save(customer);
    }
    public Customer findCustomerById(String customerId){
        Customer customer=customerRepository.findById(customerId);
        if(customer==null){
            throw new CustomerNotFoundException("Customer not found");
        }
        return customer;
    }
    public Collection<Customer> findAllCustomers(){
        return Collections.unmodifiableCollection(customerRepository.findAll());
    }
    public void updateMobileNumber(String customerId, String mobileNumber){
        Customer customer=findCustomerById(customerId);
        customer.setMobileNumber(mobileNumber);
    }
    public void updateEmail(String customerId, String email){
        Customer customer=findCustomerById(customerId);
        customer.setEmailId(email);
    }
    public void updateKycStatus(String customerId, KycStatus kycStatus){
        Customer customer=findCustomerById(customerId);
        customer.setKycStatus(kycStatus);
    }
    public void updateAddress(String customerId, String address){
        Customer customer=findCustomerById(customerId);
        customer.setAddress(address);
    }
    public void updateCustomerStatus(String customerId, CustomerStatus customerStatus){
        Customer customer=findCustomerById(customerId);
        customer.setCustomerStatus(customerStatus);
    }
    public void updateNomineeName(String customerId, String nomineeName){
        Customer customer=findCustomerById(customerId);
        customer.setNomineeName(nomineeName);
    }
    public void deactivateCustomer(String customerId){
        updateCustomerStatus(customerId,CustomerStatus.DEACTIVATED);
    }
}
