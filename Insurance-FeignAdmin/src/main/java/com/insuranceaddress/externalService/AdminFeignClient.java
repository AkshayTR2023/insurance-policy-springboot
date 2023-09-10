package com.insuranceaddress.externalService;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.insuranceaddress.DTO.Admin;
import com.insuranceaddress.DTO.Customer;

@FeignClient(name="ADMIN-SERVICE")
public interface AdminFeignClient {
	
	@GetMapping("/cust/{customerId}")
	Customer getCustomer(@PathVariable("customerId") long customerId);

}
