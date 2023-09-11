package com.insurance.dto;

import com.insurance.entity.Customer;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CustomerWithAddress {
	private Long customerId;
	private String customerEmail;
	private String customerName;
	private String customerPassword;
	private int customerAge;
	private String customerGender;
	private String customerMobile;
	private String customerImageUrl;
	private Address customerAddress;
	
	public CustomerWithAddress(Customer customer, Address address) {
        this.customerId = customer.getCustomerId();
        this.customerEmail = customer.getCustomerEmail();
        this.customerName = customer.getCustomerName();
        this.customerPassword = customer.getCustomerPassword();
        this.customerAge = customer.getCustomerAge();
        this.customerGender = customer.getCustomerGender();
        this.customerMobile = customer.getCustomerMobile();
        this.customerImageUrl = customer.getCustomerImageUrl();
        this.customerAddress = address;
    }
	
}
