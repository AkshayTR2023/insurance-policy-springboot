package com.insurance.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.insurance.entity.Customer;
import com.insurance.repository.CustomerRepository;

@Service(value = "customerService")
@Scope(value = "singleton")
public class CustomerService implements ICustomerService {

	@Autowired
	@Qualifier(value = "customerRepository")
	private CustomerRepository customerRepository;

	@Override
	public Customer registerCustomer(Customer customer) {
		return customerRepository.save(customer);
	}

	@Override
	public Customer updateCustomer(Long customerId, Customer customer) {
		if (customerRepository.findById(customerId).orElse(null) != null) {
			customer.setCustomerId(customerId);
			return customerRepository.save(customer);
		}
		return null;
	}

	@Override
	public Customer checkLoginCredentials(String customerEmail, String customerPassword) {
		return customerRepository.findByCustomerEmailAndCustomerPassword(customerEmail, customerPassword);
	}

	@Override
	public Customer getCustomerById(Long customerId) {
		return customerRepository.findById(customerId).orElse(null);
	}

	@Override
	public Customer getCustomerByEmail(String customerEmail) {
		return customerRepository.findByCustomerEmail(customerEmail);
	}

	@Override
	public List<Customer> getAllCustomers() {
		return customerRepository.findAll();
	}

	@Override
	public void deleteCustomerById(Long customerId) {
		if (customerRepository.findById(customerId).orElse(null) != null)
			customerRepository.deleteById(customerId);

	}

}
