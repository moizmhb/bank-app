package com.sample.banking.app.controller;

import com.sample.banking.app.model.Transaction;
import com.sample.banking.app.service.TransactionService;
import com.sample.banking.app.wrapper.DepositWithdrawal;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

import static com.sample.banking.app.config.HttpConstant.*;
import static com.sample.banking.app.config.SwaggerConstant.*;


@CrossOrigin
@RestController
@RequestMapping("/api/transaction")
@Api(tags = { "Transactions REST endpoints" })
@Validated
public class TransactionController {

	@Autowired
	private TransactionService transactionService;

	@PostMapping(path = "/deposit")
	@ApiOperation(value = DEPOSIT_AMOUNT)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Success"),
			@ApiResponse(code = HTTP_CODE_400, message = BAD_REQUEST),
			@ApiResponse(code = HTTP_CODE_404, message = INVALID_ACCOUNT_NUMBER),
			@ApiResponse(code = HTTP_CODE_500, message = INTERNAL_SERVER_ERROR)})

	public ResponseEntity<Object> depositAmountByAccountNumber(@Valid @RequestBody  DepositWithdrawal deposit) {
		return transactionService.depositAmountByAccountNumber(deposit);
	}

	@PostMapping(path = "/withdraw")
	@ApiOperation(value = WITHDRAW_AMOUNT)
	@ApiResponses(value = { @ApiResponse(code = 200, message = SUCCESS),
			@ApiResponse(code = HTTP_CODE_400, message =  BAD_REQUEST),
			@ApiResponse(code = HTTP_CODE_404, message = INVALID_ACCOUNT_NUMBER),
			@ApiResponse(code = HTTP_CODE_500, message = INTERNAL_SERVER_ERROR)
			})

	public ResponseEntity<Object> withdrawAmountByAccountNumber(@Valid @RequestBody  DepositWithdrawal deposit) {
		return transactionService.withDrawAmountByAccountNumber(deposit);
	}

	@GetMapping(path = "/allTransactions")
	@ApiOperation(value = GET_ALL_TRANSACTIONS, notes = GET_ALL_TRANSACTIONS_BY_ACCOUNT_NUMBER)
	@ApiResponses(value = { @ApiResponse(code = 200, message = SUCCESS),
			@ApiResponse(code = HTTP_CODE_400, message = BAD_REQUEST),
			@ApiResponse(code = HTTP_CODE_404, message = INVALID_ACCOUNT_NUMBER),
			@ApiResponse(code = HTTP_CODE_500, message = INTERNAL_SERVER_ERROR) })

	public List<Transaction> getTransactionByAccountNumber(@RequestParam Long accountNumber, @RequestParam int pageNumber, @RequestParam int pageSize) {
		return transactionService.findTransactionsByAccountNumber(accountNumber, pageNumber, pageSize);
	}

	@GetMapping(path = "/count")
	@ApiOperation(value = GET_TRANSACTIONS_COUNT, notes = GET_ALL_TRANSACTIONS_COUNT)
	@ApiResponses(value = { @ApiResponse(code = 200, message = SUCCESS),
			@ApiResponse(code = 400, message = BAD_REQUEST),
			@ApiResponse(code = 500, message = INTERNAL_SERVER_ERROR) })
	public Long getTransactionCount() {
		return transactionService.getTransactionCount();
	}
}
