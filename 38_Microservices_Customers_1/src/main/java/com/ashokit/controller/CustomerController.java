package com.ashokit.controller;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ashokit.request.CustomerRequest;
import com.ashokit.response.ApiResponse;
import com.ashokit.response.CustomerResponse;
import com.ashokit.services.CustomerService;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping(value = "/api/customers")
//@RefreshScope
@Slf4j
public class CustomerController {
	
	@Autowired
	private CustomerService customerService;
	
	//@Value("${welcome.message}")
	//private String welcomeMessage;
	
	int retryCount =1;
	
	@GetMapping(value="/welcome")
	public ResponseEntity<String> getWelcomeMessage(){
		log.debug("Inside the CustomerController - getWelcomeMessage().....");
		return new ResponseEntity<String>("",HttpStatus.OK);
	}
	
	
	@GetMapping(value = "/")
	public ResponseEntity<?> fetchAllCustomers(){
		List<CustomerResponse> allCustomers = customerService.getAllCustomers();
		return ResponseEntity.ok(allCustomers);
	}
	
	@GetMapping(value = "/{customerId}")
	//@CircuitBreaker(name = "Address-Service",fallbackMethod = "errorHandlingMethod")
	//@Retry(name="Address-Service",fallbackMethod = "errorHandlingMethod")
	public ResponseEntity<?> fetchCustomerDetailsById(@PathVariable("customerId") Integer customerId){
		//FirstTechnique -> Not Readable
		//CustomerResponse customerDetails = customerService.getCustomerById(customerId);
		System.out.println("Retry Count:::" + retryCount + " Date  is :::" + LocalDateTime.now());
		//Second Technique -> More Readable
		ApiResponse customerDetails = customerService.getCustomerAndAddressById(customerId);
		return ResponseEntity.ok(customerDetails);
	}
	
	@PostMapping(value = "/")
	public ResponseEntity<?> createNewCustomer(@RequestBody CustomerRequest customerRequest){
		log.debug("Inside the CustomerController - createNewCustomer()....."+customerRequest);
		CustomerResponse newCustomerDetails = customerService.createCustomer(customerRequest);
		log.info("Customer Response:::::" + newCustomerDetails);
		return new ResponseEntity<>(newCustomerDetails, HttpStatus.CREATED);
	}
		
	/*public ResponseEntity<?> errorHandlingMethod(@PathVariable("customerId") Integer customerId,Throwable throwable) {
	    return new ResponseEntity<ApiResponse>(new ApiResponse("OOPS Something went Wrong.Address-Service is Down..."),HttpStatus.OK);
	}*/
}
