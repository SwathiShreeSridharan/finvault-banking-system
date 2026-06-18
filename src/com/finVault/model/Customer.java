package com.finVault.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import com.finVault.entity.BaseEntity;
import com.finVault.enums.CustomerStatus;
import com.finVault.enums.KycStatus;
import com.finVault.utils.IDGenerator;

public class Customer extends BaseEntity {
    private final String customerId;
    private final String customerName;
    private final LocalDate dateOfBirth;
    private String mobileNumber;
    private final String aadhaarNumber;
    private final String panNumber;
    private String emailId;
    private String address;
    private String nomineeName;
    private CustomerStatus customerStatus;
    private KycStatus kycStatus;
    private final LocalDate joinedDate;
    private final Set<String> accountNumbers;


    public Customer(String customerName, LocalDate dateOfBirth, String mobileNumber,
                    String aadhaarNumber, String panNumber, String address, String nomineeName) {
        this.customerId = IDGenerator.generateCustomerId();
        this.customerName = customerName;
        this.dateOfBirth = dateOfBirth;
        this.mobileNumber = mobileNumber;
        this.aadhaarNumber = aadhaarNumber;
        this.panNumber = panNumber;
        this.address = address;
        this.nomineeName = nomineeName;
        this.customerStatus = CustomerStatus.ACTIVE;
        this.kycStatus = KycStatus.PENDING;
        this.joinedDate = LocalDate.now();
        this.accountNumbers = new HashSet<>();
    }

    @Override
    public String toString() {
        return customerName + " (" + customerId + ")";
    }

    public String getCustomerId() {
        return customerId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public String getAadhaarNumber() {
        return aadhaarNumber;
    }

    public String getPanNumber() {
        return panNumber;
    }

    public String getEmailId() {
        return emailId;
    }

    public LocalDate getJoinedDate() {
        return joinedDate;
    }

    public Set<String> getAccountNumbers() {
        return Collections.unmodifiableSet(accountNumbers);
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
        markUpdated();
    }

    public void addAccount(String accountNumber) {
        accountNumbers.add(accountNumber);
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber=mobileNumber;
        markUpdated();
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void removeAccount(String accountNumber) {
        accountNumbers.remove(accountNumber);
        markUpdated();
    }

    public void setAddress(String address) {
        this.address = address;
        markUpdated();
    }

    public String getAddress() {
        return address;
    }

    public void setNomineeName(String nomineeName) {
        this.nomineeName = nomineeName;
        markUpdated();
    }

    public String getNomineeName() {
        return nomineeName;
    }

    public void setCustomerStatus(CustomerStatus customerStatus) {
        this.customerStatus = customerStatus;
        markUpdated();
    }

    public CustomerStatus getCustomerStatus() {
        return customerStatus;
    }

    public void setKycStatus(KycStatus kycStatus) {
        this.kycStatus = kycStatus;
        markUpdated();
    }

    public KycStatus getKycStatus() {
        return kycStatus;
    }

    private void markUpdated(){
        this.updatedAt= LocalDateTime.now();
        this.updatedBy="SYSTEM";
    }
}
