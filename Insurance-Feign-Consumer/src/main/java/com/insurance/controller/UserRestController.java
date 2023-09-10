package com.insurance.controller;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.insurance.dto.Address;
import com.insurance.dto.Customer;
import com.insurance.dto.CustomerWithAddress;
import com.insurance.dto.Policy;
import com.insurance.dto.PolicyCategory;
import com.insurance.proxy.AddressServiceProxy;
import com.insurance.proxy.AdminServiceProxy;
import com.insurance.proxy.ContactFormServiceProxy;
import com.insurance.proxy.CustomerServiceProxy;
import com.insurance.proxy.PolicyCategoryServiceProxy;
import com.insurance.proxy.PolicyServiceProxy;
import com.insurance.proxy.QuestionServiceProxy;

@RestController(value = "userRestController")
@Scope(value = "request")
@RequestMapping("/user-feign")
@CrossOrigin(origins = "*")
public class UserRestController {
	@Autowired
	AdminServiceProxy adminServiceProxy;
	@Autowired
	CustomerServiceProxy customerServiceProxy;
	@Autowired
	AddressServiceProxy addressServiceProxy;
	@Autowired
	ContactFormServiceProxy contactFormServiceProxy;
	@Autowired
	PolicyCategoryServiceProxy categoryServiceProxy;
	@Autowired
	PolicyServiceProxy policyServiceProxy;
	@Autowired
	QuestionServiceProxy questionServiceProxy;
	private Logger log = LoggerFactory.getLogger(UserRestController.class);
	// =============================USER======================================//

	// ==============================POST=================================//
	@PostMapping(value = "/customer/register")
	public ResponseEntity<Customer> registerCustomer(@RequestBody Customer customer) {
		return customerServiceProxy.registerCustomer(customer);
	}

	@PostMapping(value = "/customer/login")
	public ResponseEntity<Customer> checkCustomerLogin(@RequestBody Customer customer) {
		return customerServiceProxy.customerLoginCheck(customer);
	}

	@PostMapping(value = "/customer/add-address/{customerId}")
	public ResponseEntity<CustomerWithAddress> addCustomerAddress(@PathVariable("customerId") Long customerId,
			@RequestBody Address address) {
		Address addedAddress = addressServiceProxy.addAddress(customerId, address).getBody();
		Customer customer = customerServiceProxy.getCustomerById(customerId).getBody();
		if (addedAddress != null)
			return new ResponseEntity<>(new CustomerWithAddress(customer, addedAddress), HttpStatus.CREATED);
		return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
	}

	// ==============================PUT=================================//
	@PutMapping(value = "/customer/{customerId}")
	public ResponseEntity<CustomerWithAddress> updateCustomer(@PathVariable("customerId") Long customerId,
			@RequestBody CustomerWithAddress customerWithAddress) {
		Customer customer = customerServiceProxy
				.updateCustomer(customerId,
						new Customer(customerId, customerWithAddress.getCustomerEmail(),
								customerWithAddress.getCustomerName(), customerWithAddress.getCustomerPassword(),
								customerWithAddress.getCustomerAge(), customerWithAddress.getCustomerGender(),
								customerWithAddress.getCustomerMobile(), customerWithAddress.getCustomerImageUrl()))
				.getBody();
		Address address = addressServiceProxy.updateAddress(customerId, customerWithAddress.getCustomerAddress())
				.getBody();
		if (address != null && customer != null)
			return new ResponseEntity<>(new CustomerWithAddress(customer, address), HttpStatus.OK);
		return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
	}
	// ==============================GET=================================//
	@GetMapping(value="/customer/{customerId}")
	public ResponseEntity<Customer> getBasicCustomerDetails(@PathVariable("customerId")Long customerId){
		return customerServiceProxy.getCustomerById(customerId);
	}
	
	@GetMapping(value="/customer/details-with-address/{customerId}")
	public ResponseEntity<CustomerWithAddress> getCustomerDetailsWithAddress(@PathVariable("customerId")Long customerId){
		Customer customer =customerServiceProxy.getCustomerById(customerId).getBody();
		Address address=addressServiceProxy.getAddressById(customerId).getBody();
		if (address != null && customer != null)
			return new ResponseEntity<>(new CustomerWithAddress(customer, address), HttpStatus.OK);
		return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
		
	}
	// =============================POLICY CATEGORIES & POLICIES======================================//
	//========================GET CATEGORIES=========================================================//
	@GetMapping(value = "/category")
	public List<PolicyCategory> getAllCategories() {
		log.debug("inside getAllCategoriesCategory");
		return categoryServiceProxy.getAllCategories();
	}

	@GetMapping(value = "/category/name-like/{namePhrase}")
	public List<PolicyCategory> getAllCategoriesByNameLike(@PathVariable("namePhrase") String namePhrase) {
		log.debug("inside getAllCategoriesByNameLike");
		return categoryServiceProxy.getAllCategoriesByNameLike(namePhrase);
	}

	@GetMapping(value = "/category/{categoryId}")
	public ResponseEntity<PolicyCategory> getCategoryById(@PathVariable("categoryId") Long categoryId) {
		log.debug("inside getCategoryById");
		return categoryServiceProxy.getCategoryById(categoryId);
	}

	@GetMapping(value = "/category/name/{categoryName}")
	public PolicyCategory getCategoryByName(@PathVariable("categoryName") String categoryName) {
		log.debug("inside getCategoryByName");
		return categoryServiceProxy.getCategoryByName(categoryName);
	}
	
	//========================GET POLICIES=========================================================//
	@GetMapping(value = "/policy")
	public List<Policy> getAllPolicies() {
		log.debug("inside getAllPolicies");
		return policyServiceProxy.getAllPolicies();
	}

	@GetMapping(value = "/policy/{policyId}")
	public Policy getPolicyById(@PathVariable("policyId") Long policyId) {
		log.debug("inside getPolicyById");
		return policyServiceProxy.getPolicyById(policyId);
	}

	@GetMapping(value = "/policy/name-like/{namePhrase}")
	public List<Policy> getAllPoliciesByNameLike(@PathVariable("namePhrase") String namePhrase) {
		log.debug("inside getAllPoliciesByNameLike");
		return policyServiceProxy.getAllPoliciesByNameLike(namePhrase);
	}

	@GetMapping(value = "/policy/name/{policyName}", produces = MediaType.APPLICATION_JSON_VALUE)
	public Policy getPolicyByName(@PathVariable("policyName") String policyName) {
		log.debug("inside getPolicyByName");
		return policyServiceProxy.getPolicyByName(policyName);
	}
	@GetMapping(value="/policy/by-category/{categoryId}")
	public List<Policy> getPoliciesByCategory(@PathVariable("categoryId") Long categoryId) {
		log.debug("inside getPoliciesByCategory");
		PolicyCategory category= categoryServiceProxy.getCategoryById(categoryId).getBody();
		List<Long> policyIds=category.getPolicyIds();
		
		List<Policy> policies = new ArrayList<>();
		
		for(Long policyId : policyIds) {
			Policy policy = policyServiceProxy.getPolicyById(policyId);
			policies.add(policy);
		}
		return policies;
	}
	//============================APPLY FOR A POLICY==================================//
	//@PostMapping(value="/policy/apply/{policyId}/")
}
