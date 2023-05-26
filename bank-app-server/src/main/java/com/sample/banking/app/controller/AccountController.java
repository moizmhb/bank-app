package com.sample.banking.app.controller;

import com.sample.banking.app.service.impl.AccountServiceImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.sample.banking.app.config.HttpConstant.*;
import static com.sample.banking.app.config.SwaggerConstant.*;

@CrossOrigin
@RestController
@RequestMapping("/api/accounts")
@Api(tags = { "Accounts REST endpoints" })
public class AccountController {
	@Autowired
	private AccountServiceImpl accountService;

	private static final Logger log = LoggerFactory.getLogger(AccountController.class);

	@GetMapping(path = "/{accountNumber}")
	@ApiOperation(value = GET_ACCOUNT_DETAILS, notes = FIND_ACCOUNT_DETAILS_BY_ACCOUNT_NUMBER)
	@ApiResponses(value = { @ApiResponse(code = 200, message = SUCCESS),
			@ApiResponse(code = HTTP_CODE_400, message = BAD_REQUEST),
			@ApiResponse(code = HTTP_CODE_404, message = INVALID_ACCOUNT_NUMBER),
			@ApiResponse(code = HTTP_CODE_500, message = INTERNAL_SERVER_ERROR)
			})
	public ResponseEntity<Object> getByAccountNumber(@PathVariable Long accountNumber) {
		log.info("Fetching account details by account number :{}",accountNumber);
		return accountService.findByAccountNumber(accountNumber);
	}

}
