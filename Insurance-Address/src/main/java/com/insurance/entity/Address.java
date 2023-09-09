package com.insurance.entity;

import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Address {

	@Id
	private Long addressId;
	private String streetAddress;
	private String city;
	private String state;
	private String postalCode;
	private String country;
}
