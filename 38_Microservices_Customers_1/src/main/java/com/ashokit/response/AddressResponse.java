package com.ashokit.response;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(value = Include.NON_NULL)
public class AddressResponse {

	private Integer id;

	private String doorNo;

	private String cityName;

	private String pincode;

	private LocalDateTime created_dt;

	private LocalDateTime updated_dt;

	private Integer customerId;

}
