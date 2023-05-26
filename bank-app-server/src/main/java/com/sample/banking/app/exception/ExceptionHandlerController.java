package com.sample.banking.app.exception;


import com.sample.banking.app.config.ErrorMessageConstant;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionHandlerController {

    @ExceptionHandler(InsufficientBalanceException.class)

    public ResponseEntity<?> handleInsufficientBalanceException() {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ErrorMessageConstant.INSUFFICIENT_BALANCE_EXCEPTION_MESSAGE);
    }

    @ExceptionHandler(InvalidAccountException.class)
    public ResponseEntity<?> handleInvalidAccountException() {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ErrorMessageConstant.INVALID_ACCOUNT_EXCEPTION_MESSAGE);
    }

    @ExceptionHandler(InvalidCustomerException.class)
    public ResponseEntity<?> handleInvalidCustomerException() {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ErrorMessageConstant.INVALID_CUSTOMER_EXCEPTION_MESSAGE);
    }

    @ExceptionHandler(WeakPasswordException.class)
    public ResponseEntity<?> handleWeakPasswordException() {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(com.ega.banking.dto.ExcetionDto.builder().message(ErrorMessageConstant.WEAK_PASSWORD)
                        .build());
    }

    @ExceptionHandler(InvalidAmountException.class)
    public ResponseEntity<?> handleInvalidAmountException() {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ErrorMessageConstant.INVALID_AMOUNT_EXCEPTION_MESSAGE);
    }
}
