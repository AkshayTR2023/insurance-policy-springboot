package com.insuranceaddress.service;

import org.springframework.beans.factory.annotation.Autowired;

import com.insuranceaddress.DTO.Customer;
import com.insuranceaddress.externalService.AdminFeignClient;

public class AdminFeignService {
	
	@Autowired
	AdminFeignClient adminFeignClient;
	
	  public Customer getbyid(long id) {
	    	Customer customer = adminFeignClient.getCustomer(id);
	    
			return customer;
	    	
	    }

}
