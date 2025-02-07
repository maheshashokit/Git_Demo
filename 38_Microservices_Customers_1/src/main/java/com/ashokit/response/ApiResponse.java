package com.ashokit.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(value = Include.NON_NULL)
public class ApiResponse {
	
	private CustomerResponse customerDetails;
	
	private AddressResponse addressDetails;
	
	/*private String errorMessage;
	
	public ApiResponse(String errorMessage) {
		this.errorMessage = errorMessage;
	}*/
	
}
