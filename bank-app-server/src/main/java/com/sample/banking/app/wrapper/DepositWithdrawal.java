package com.sample.banking.app.wrapper;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class DepositWithdrawal {

    @NotNull
    Long accountNumber;

    @NotNull
    Long  amount;
}
