package com.sample.banking.app.config;

public class BankConstant {

    public static final String DEPOSIT = "Deposit";
    public static final String WITHDRAWAL = "Withdraw";
    public static final String ACCOUNT_STATUS_ACTIVE = "Active";
    public static final String TYPE_SAVING = "Saving";

    public static final String STATUS = "status";
    public static final String UNAUTHORIZED = "Unauthorized";
    public static final String ERROR = "error";
    public static final String MESSAGE = "message";
    public static final String PATH = "path";

    public static final int MIN_LENGTH_NAME = 3;
    public static final int MAX_LENGTH_NAME = 10;

    public static final Long MAX_AMOUNT = 1000000L;
    public static final Long MIN_AMOUNT = 0L;
    private BankConstant(){}

}
