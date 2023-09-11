package com.insurance.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Address {

	private Long addressId;
	private String streetAddress;
	private String city;
	private String state;
	private String postalCode;
	private String country;
}
