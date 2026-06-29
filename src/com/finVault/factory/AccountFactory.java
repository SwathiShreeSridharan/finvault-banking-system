package com.finVault.factory;

import com.finVault.enums.AccountType;
import com.finVault.exception.InvalidTransactionException;
import com.finVault.model.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Map;

public class AccountFactory {
    public static Account createAccount(AccountType type, Customer customer,
                                        String pin, String createdBy, Map<String, Object> params) {
        if(type == null) throw new InvalidTransactionException("Account type is null");
        if(params == null) throw new InvalidTransactionException("Parameters cannot be null");
        return switch (type) {
            case SAVINGS -> {
                BigDecimal interestRate = BigDecimal.valueOf(getDouble(params,"interestRate",3.5));
                int withdrawalLimit = getInt(params,"withdrawalLimit",20);
                BigDecimal minBalance = BigDecimal.valueOf(getDouble(params,"minBalance", 1000));

                yield new SavingsAccount(customer,
                        interestRate, withdrawalLimit,
                        createdBy, minBalance,
                        pin);
            }
            case CURRENT -> {
                BigDecimal overdraftLimit = BigDecimal.valueOf(getDouble(params,"overdraftLimit", 2500));
                BigDecimal transactionFee = BigDecimal.valueOf(getDouble(params,"transactionFee", 50));

                yield new CurrentAccount(customer,
                        pin, createdBy,
                        overdraftLimit,
                        transactionFee);
            }
            case FIXED_DEPOSIT -> {
                BigDecimal principalAmount = BigDecimal.valueOf(requireDouble(params,"principalAmount"));
                BigDecimal interestRate = BigDecimal.valueOf(getDouble(params,"interestRate", 6));
                int tenureInMonths = requireInt(params,"tenureInMonths");
                BigDecimal penaltyRate = BigDecimal.valueOf(getDouble(params,"penaltyRate", 1));
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
        double number;

        if (value instanceof Number) {
            number = ((Number) value).doubleValue();
        } else if (value instanceof String) {
            String str = ((String) value).trim();
            try {
                number = Double.parseDouble(str);
            } catch (NumberFormatException e) {
                throw new InvalidTransactionException(
                        "Parameter '" + key + "' is not a valid number: '" + str + "'");
            }
        } else {
            throw new InvalidTransactionException(
                    "Parameter '" + key + "' must be numeric or a numeric string, got: "
                            + value.getClass().getSimpleName());
        }

        if(number<=0)
            throw new  InvalidTransactionException("Invalid value for required parameter: " + key);
        return number;
    }

    private static int requireInt(Map<String, Object> params, String key) {
        Object value=params.get(key);
        if(value==null)
            throw new  InvalidTransactionException("Missing required parameter: " + key);
        int number;
        if (value instanceof Number) {
            number = ((Number) value).intValue();
        } else if (value instanceof String) {
            String str = ((String) value).trim();
            try {
                number = Integer.parseInt(str);
            } catch (NumberFormatException e) {
                throw new InvalidTransactionException(
                        "Parameter '" + key + "' is not a valid number: '" + str + "'");
            }
        } else {
            throw new InvalidTransactionException(
                    "Parameter '" + key + "' must be numeric or a numeric string, got: "
                            + value.getClass().getSimpleName());
        }
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
