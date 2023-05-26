package com.sample.banking.app.controller;

import com.sample.banking.app.model.Customer;
import com.sample.banking.app.service.impl.CustomerServiceImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.sample.banking.app.config.HttpConstant.*;
import static com.sample.banking.app.config.SwaggerConstant.*;

@CrossOrigin
@RestController
@RequestMapping("/api/customers")
@Api(tags = { "Customer REST endpoints" })
public class CustomerController {

	@Autowired
	private CustomerServiceImpl customerService;

	@GetMapping(path = "/all")
	@ApiOperation(value = FIND_ALL_CUSTOMERS, notes = GETS_DETAILS_OF_ALL_THE_CUSTOMERS)
	@ApiResponses(value = { @ApiResponse(code = HTTP_CODE_200, message = SUCCESS),
			@ApiResponse(code = HTTP_CODE_400, message = BAD_REQUEST),
			@ApiResponse(code = HTTP_CODE_500, message = INTERNAL_SERVER_ERROR) })
	public List<Customer> getAllCustomers() {

		return customerService.findAll();
	}

	@GetMapping(path = "/{customerNumber}")
	@ApiOperation(value = GET_CUSTOMER_DETAILS, notes = GET_CUSTOMER_DETAILS_BY_CUSTOMER_NUMBER)
	@ApiResponses(value = {
			@ApiResponse(code = HTTP_CODE_200, message = SUCCESS, response = Customer.class, responseContainer = "Object"),
			@ApiResponse(code = HTTP_CODE_400, message = BAD_REQUEST),
			@ApiResponse(code = HTTP_CODE_404, message = INVALID_CUSTOMER_NUMBER),
			@ApiResponse(code = HTTP_CODE_500, message = INTERNAL_SERVER_ERROR)})

	public Customer getCustomer(@PathVariable Long customerNumber) {

		return customerService.findByCustomerNumber(customerNumber);
	}

}
