package com.insurance.service;

import java.util.List;

import com.insurance.entity.Customer;

public interface ICustomerService {

	public Customer registerCustomer(Customer customer);

	public Customer updateCustomer(Long customerId, Customer customer);

	public Customer checkLoginCredentials(String customerEmail, String customerPassword);

	public Customer getCustomerById(Long customerId);

	public Customer getCustomerByEmail(String customerEmail);

	public List<Customer> getAllCustomers();

	public void deleteCustomerById(Long customerId);

}
