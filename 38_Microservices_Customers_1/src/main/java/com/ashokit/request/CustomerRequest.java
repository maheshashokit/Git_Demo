package com.ashokit.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CustomerRequest {
	
	private String name;
	
	private String location;
	
	private String gender;
	
	private String emailId;
	
	private String contactNo;
}