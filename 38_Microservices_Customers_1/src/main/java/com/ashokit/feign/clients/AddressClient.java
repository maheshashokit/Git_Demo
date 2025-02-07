package com.ashokit.feign.clients;

import java.util.List;

//import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.ashokit.response.AddressResponse;

//@FeignClient(name="ADDRESS-SERVICE", path="/api/address")
public interface AddressClient {

	@GetMapping(value = "/")
	public ResponseEntity<List<AddressResponse>> fetchAllAddresses();
	
	@GetMapping(value = "/customer/{customerId}")
	public ResponseEntity<AddressResponse> fetchAddressByCustomerId(@PathVariable("customerId") Integer customerId);
	
}