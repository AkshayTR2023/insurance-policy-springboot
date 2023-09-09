package com.insurance.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.insurance.entity.Customer;
import com.insurance.service.ICustomerService;

@RestController(value = "customerController")
@Scope(value = "request")
@RequestMapping("/customer")
@CrossOrigin(origins = "*")
public class CustomerController {

	@Autowired
	@Qualifier(value = "customerService")
	private ICustomerService customerService;

	// ==============================POST=================================//
	@PostMapping(value = "/register", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(value = HttpStatus.CREATED)
	public ResponseEntity<Customer> registerCustomer(@RequestBody Customer customer) {
	    if (customerService.getCustomerByEmail(customer.getCustomerEmail()) != null) {
	        return ResponseEntity.status(HttpStatus.CONFLICT).build();
	    }
	    Customer registeredCustomer = customerService.registerCustomer(customer);
	    return ResponseEntity.status(HttpStatus.CREATED).body(registeredCustomer);
	}


	@PostMapping(value = "/login", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Customer> customerLoginCheck(@RequestBody Customer customer) {
		Customer checkCustomer = customerService.checkLoginCredentials(customer.getCustomerEmail(),
				customer.getCustomerPassword());
		if (checkCustomer != null) {
			return new ResponseEntity<>(checkCustomer, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		}
	}

	// ==============================PUT=================================//
	@PutMapping(value = "/{customerId}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Customer> updateCustomer(@PathVariable("customerId") Long customerId,
			@RequestBody Customer customer) {
		Customer updatedCustomer = customerService.updateCustomer(customerId, customer);
		if (updatedCustomer != null) {
			return new ResponseEntity<>(updatedCustomer, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	// ==============================GET=================================//

	@GetMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
	public List<Customer> getAllCustomers() {
		return customerService.getAllCustomers();
	}

	@GetMapping(value = "/{customerId}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Customer> getCustomerById(@PathVariable("customerId") Long customerId) {
		Customer customer = customerService.getCustomerById(customerId);
		if (customer != null)
			return new ResponseEntity<>(customer, HttpStatus.OK);
		return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
	}

	@GetMapping(value = "/email/{customerEmail}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Customer> getCustomerByEmail(@PathVariable("customerEmail") String customerEmail) {
		Customer customer = customerService.getCustomerByEmail(customerEmail);
		if (customer != null)
			return new ResponseEntity<>(customer, HttpStatus.OK);
		return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
	}

	// ==============================DEL=================================//

	@DeleteMapping(value = "/{customerId}")
	@ResponseStatus(value = HttpStatus.NO_CONTENT)
	public ResponseEntity<Void> deleteCustomerById(@PathVariable("customerId") Long customerId) {
		if (customerService.getCustomerById(customerId) != null) {
			customerService.deleteCustomerById(customerId);
			return ResponseEntity.noContent().build();
		} else {
			return ResponseEntity.notFound().build();
		}
	}
}
