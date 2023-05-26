package com.sample.banking.app.config;

public final class ErrorMessageConstant {

    public static final String INSUFFICIENT_BALANCE_EXCEPTION_MESSAGE = "User does not have sufficient balance.";
    public static final String INVALID_ACCOUNT_EXCEPTION_MESSAGE = "Account does not exist. Please enter a valid account number";
    public static final String INVALID_CUSTOMER_EXCEPTION_MESSAGE = "Customer does not exist. Please enter a valid customer number";

    public static final String INVALID_AMOUNT_EXCEPTION_MESSAGE = "Invalid amount";
    public static final String ERROR_USERNAME_IS_ALREADY_TAKEN = "Error: Username is already taken!";
    public static final String ERROR_EMAIL_IS_ALREADY_IN_USE = "Error: Email is already in use!";

    public static final String ERROR_ROLE_IS_NOT_FOUND = "Error: Role is not found.";

    public static final String USER_REGISTERED_SUCCESSFULLY = "User registered successfully!";

    public static final String USER_ALREADY_REGISTERED = "User already registered!";
    public static final String INVALID_FIRST_NAME = "Invalid firstname";
    public static final String INVALID_LAST_NAME = "Invalid lastname";

    public static final String INVALID_USER_NAME = "Invalid username";
    public static final String INVALID_EMAIL = "Invalid email";

    public static final String INVALID_AMOUNT = "Invalid amount";
    public static final String WEAK_PASSWORD= "Password must contain at least 1 upper case letter, " +
            "at least 1 lower case letter, " +
            "at least 1 number and at least 1 special character and must be of size 8";
    private ErrorMessageConstant(){}
}
