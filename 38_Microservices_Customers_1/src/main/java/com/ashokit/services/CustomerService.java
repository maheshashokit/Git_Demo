package com.ashokit.services;

import java.util.List;

import com.ashokit.request.CustomerRequest;
import com.ashokit.response.ApiResponse;
import com.ashokit.response.CustomerResponse;

public interface CustomerService {
	
	public CustomerResponse createCustomer(CustomerRequest customerRequest);
	
	public CustomerResponse getCustomerById(Integer customerId);
	
	public List<CustomerResponse> getAllCustomers();
	
	//getting customer along with his address information
	public ApiResponse getCustomerAndAddressById(Integer customerId);
	

}