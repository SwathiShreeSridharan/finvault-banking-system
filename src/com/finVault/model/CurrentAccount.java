package com.finVault.model;

import com.finVault.enums.AccountStatus;
import com.finVault.enums.AccountType;
import com.finVault.enums.TransactionStatus;
import com.finVault.enums.TransactionType;
import com.finVault.exception.InsufficientFundsException;

public class CurrentAccount extends Account {
    private final double overdraftLimit;
    private final double transactionFee;

    public CurrentAccount(Customer customerName,String pin, String createdBy, double overdraftLimit, double transactionFee) {
        super(customerName, AccountType.CURRENT, AccountStatus.ACTIVE, pin, createdBy);
        this.overdraftLimit=overdraftLimit;
        this.transactionFee=transactionFee;
    }

    @Override
    public void withdraw(double amount) {
        validateAmount(amount);
        validateActiveAccount();
        double newBalance=getBalance()-amount-transactionFee;
        if(newBalance<-overdraftLimit){
            double shortfall=Math.abs(newBalance)-overdraftLimit;
            throw new InsufficientFundsException("Balance can not go below overdraft limit",shortfall);
        }

        setBalance(newBalance);
        recordTransaction(TransactionType.WITHDRAWAL,amount,
        getAccountNumber(),null, TransactionStatus.SUCCESS,
                "Cash Withdrawal");
    }

    @Override
    public double calculateInterest() {
        return 0.0;
    }

    public double getOverdraftLimit() { return overdraftLimit; }
    public double getOverdraftUsed() {
        return getBalance()<0? Math.abs(getBalance()):0;
    }
    public double getTransactionFee() { return transactionFee; }
}
