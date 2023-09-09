package com.insurance.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerWithAddress {
	private Long customerId;
	private String customerEmail;
	private String customerName;
	private String customerPassword;
	private String customerAge;
	private String customerGender;
	private String customerMobile;
	private String customerImageUrl;
	private Address customerAddress;

	
}
