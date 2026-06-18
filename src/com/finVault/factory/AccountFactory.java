package com.finVault.factory;

import com.finVault.enums.AccountType;
import com.finVault.exception.InvalidTransactionException;
import com.finVault.model.*;

import java.time.LocalDate;
import java.util.Map;

public class AccountFactory {
    public static Account createAccount(AccountType type, Customer customer,
                                        String pin, String createdBy, Map<String, Object> params) {
        if(type == null) throw new InvalidTransactionException("Account type is null");
        if(params == null) throw new InvalidTransactionException("Parameters cannot be null");
        return switch (type) {
            case SAVINGS -> {
                double interestRate = getDouble(params,"interestRate",3.5);
                int withdrawalLimit = getInt(params,"withdrawalLimit",20);
                double minBalance = getDouble(params,"minBalance", 1000);

                yield new SavingsAccount(customer,
                        interestRate, withdrawalLimit,
                        createdBy, minBalance,
                        pin);
            }
            case CURRENT -> {
                double overdraftLimit = getDouble(params,"overdraftLimit", 2500);
                double transactionFee = getDouble(params,"transactionFee", 50);

                yield new CurrentAccount(customer,
                        pin, createdBy,
                        overdraftLimit,
                        transactionFee);
            }
            case FIXED_DEPOSIT -> {
                double principalAmount = requireDouble(params,"principalAmount");
                double interestRate = getDouble(params,"interestRate", 6);
                int tenureInMonths = requireInt(params,"tenureInMonths");
                int penaltyRate = getInt(params,"penaltyRate", 100);
                LocalDate startDate = requireDate(params,"startDate");

                yield new FixedDepositAccount(customer,
                        pin, createdBy, principalAmount,
                        interestRate, tenureInMonths,
                        penaltyRate, startDate
            );
            }
        };
    }

    private static double getDouble(Map<String, Object> params, String key, double defaultVal) {
        return params.get(key) != null ? ((Number) params.get(key)).doubleValue() : defaultVal;
    }

    private static int getInt(Map<String, Object> params, String key, int defaultVal) {
        return params.get(key) != null ? ((Number) params.get(key)).intValue() : defaultVal;
    }

    private static double requireDouble(Map<String, Object> params, String key) {
        Object value = params.get(key);
        if(value==null)
            throw new  InvalidTransactionException("Missing required parameter: " + key);
        double number = ((Number) value).doubleValue();
        if(number<=0)
            throw new  InvalidTransactionException("Invalid value for required parameter: " + key);
        return number;
    }

    private static int requireInt(Map<String, Object> params, String key) {
        Object value=params.get(key);
        if(value==null)
            throw new  InvalidTransactionException("Missing required parameter: " + key);
        int number = ((Number) value).intValue();
        if(number<=0)
            throw new  InvalidTransactionException("Invalid value for required parameter: " + key);
        return number;
    }

    private static LocalDate requireDate(Map<String, Object> params, String key) {
        Object value = params.get(key);
        if(value==null)
            throw new  InvalidTransactionException("Missing required parameter: " + key);
        if(!(value instanceof LocalDate))
            throw new  InvalidTransactionException("Invalid date type: " + value);
        return (LocalDate) value;
    }
}
