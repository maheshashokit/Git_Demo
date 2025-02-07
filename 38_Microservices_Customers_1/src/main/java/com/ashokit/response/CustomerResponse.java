package com.ashokit.response;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonInclude(value = Include.NON_NULL)
public class CustomerResponse {
	
	private Integer id;

	private String name;
	
	private String location;
	
	private String gender;
	
	private String emailId;
	
	private String contactNo;
	
	private LocalDateTime createdDate;
	
	private LocalDateTime updatedDate;	
	
	//Supporting the AddressResponse
	private AddressResponse addressResponse;

}
