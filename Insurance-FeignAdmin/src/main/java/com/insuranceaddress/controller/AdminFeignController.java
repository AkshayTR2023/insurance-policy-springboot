package com.insuranceaddress.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.insuranceaddress.DTO.Customer;
import com.insuranceaddress.service.AdminFeignService;



@RestController
public class AdminFeignController {

	@Autowired
	AdminFeignService  adminService;
	@GetMapping("/cust/{customerId}")
	public Customer getByid(@PathVariable("customerId") long customerId) {
		return adminService.getbyid(customerId);
		
	}
}
