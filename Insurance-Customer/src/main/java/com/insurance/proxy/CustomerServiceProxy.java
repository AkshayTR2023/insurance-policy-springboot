package com.insurance.proxy;

import java.util.Collections;
import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.insurance.entity.Customer;
import com.insurance.exception.NotFoundException;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;

@FeignClient(name = "customer-service")
public interface CustomerServiceProxy {

	@Retry(name = "user-feign-retry")
	@CircuitBreaker(name = "user-feign-cb", fallbackMethod = "fallbackForRegisterCustomer")
	@PostMapping(value = "/customer/register", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Customer> registerCustomer(@RequestBody Customer customer);

	@Retry(name = "user-feign-retry")
	@CircuitBreaker(name = "user-feign-cb", fallbackMethod = "fallbackForCustomerLoginCheck")
	@PostMapping(value = "/customer/login", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Customer> customerLoginCheck(@RequestBody Customer customer);

	@Retry(name = "user-feign-retry")
	@CircuitBreaker(name = "user-feign-cb", fallbackMethod = "fallbackForUpdateCustomer")
	@PutMapping(value = "/customer/{customerId}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Customer> updateCustomer(@PathVariable("customerId") Long customerId,
			@RequestBody Customer customer);

	@Retry(name = "user-feign-retry")
	@CircuitBreaker(name = "user-feign-cb", fallbackMethod = "fallbackForGetAllCustomers")
	@GetMapping(value = "/customer", produces = MediaType.APPLICATION_JSON_VALUE)
	public List<Customer> getAllCustomers();

	@Retry(name = "user-feign-retry")
	@CircuitBreaker(name = "user-feign-cb", fallbackMethod = "fallbackForGetCustomerById")
	@GetMapping(value = "/customer/{customerId}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Customer> getCustomerById(@PathVariable("customerId") Long customerId);

	@Retry(name = "user-feign-retry")
	@CircuitBreaker(name = "user-feign-cb", fallbackMethod = "fallbackForGetCustomerByEmail")
	@GetMapping(value = "/customer/email/{customerEmail}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Customer> getCustomerByEmail(@PathVariable("customerEmail") String customerEmail);

	// ==============================FALLBACKS=================================//
	public default ResponseEntity<Customer> fallbackForRegisterCustomer(Customer customer, Throwable cause) {
		if (cause instanceof NotFoundException) {
			throw (NotFoundException) cause;
		}
		System.err.println("Exception: => " + cause.getMessage());
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
				.body(new Customer(0L, "defualt", "defualt", "defualt", 0, "defualt", "defualt", "defualt"));
	}

	public default ResponseEntity<Customer> fallbackForCustomerLoginCheck(Customer customer, Throwable cause) {
		if (cause instanceof NotFoundException) {
			throw (NotFoundException) cause;
		}
		System.err.println("Exception: => " + cause.getMessage());
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
				.body(new Customer(0L, "defualt", "defualt", "defualt", 0, "defualt", "defualt", "defualt"));
	}

	public default ResponseEntity<Customer> fallbackForUpdateCustomer(Long customerId, Customer customer,
			Throwable cause) {
		if (cause instanceof NotFoundException) {
			throw (NotFoundException) cause;
		}
		System.err.println("Exception: => " + cause.getMessage());
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
				.body(new Customer(customerId, "defualt", "defualt", "defualt", 0, "defualt", "defualt", "defualt"));
	}

	public default List<Customer> fallbackForGetAllCustomers(Throwable cause) {
		if (cause instanceof NotFoundException) {
			throw (NotFoundException) cause;
		}
		System.err.println("Exception: => " + cause.getMessage());
		return Collections.emptyList();
	}

	public default ResponseEntity<Customer> fallbackForGetCustomerById(Long customerId, Throwable cause) {
		if (cause instanceof NotFoundException) {
			throw (NotFoundException) cause;
		}
		System.err.println("Exception: => " + cause.getMessage());
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
				.body(new Customer(customerId, "defualt", "defualt", "defualt", 0, "defualt", "defualt", "defualt"));
	}

	public default ResponseEntity<Customer> fallbackForGetCustomerByEmail(String customerEmail, Throwable cause) {
		if (cause instanceof NotFoundException) {
			throw (NotFoundException) cause;
		}
		System.err.println("Exception: => " + cause.getMessage());
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
				.body(new Customer(0L, customerEmail, "defualt", "defualt", 0, "defualt", "defualt", "defualt"));
	}
}
