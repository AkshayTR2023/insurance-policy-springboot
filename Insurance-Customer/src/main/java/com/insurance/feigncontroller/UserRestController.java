package com.insurance.feigncontroller;

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
import com.insurance.dto.AppliedPolicyWithCustomer;
import com.insurance.dto.ContactForm;
import com.insurance.dto.CustomerWithAddress;
import com.insurance.dto.IssuePolicy;
import com.insurance.dto.Policy;
import com.insurance.dto.PolicyCategory;
import com.insurance.dto.PolicyHistory;
import com.insurance.dto.Question;
import com.insurance.entity.Customer;
import com.insurance.proxy.AddressServiceProxy;
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
		log.debug("inside registerCustomer");
		return customerServiceProxy.registerCustomer(customer);
	}

	@PostMapping(value = "/customer/login")
	public ResponseEntity<Customer> checkCustomerLogin(@RequestBody Customer customer) {
		log.debug("inside checkCustomerLogin");
		return customerServiceProxy.customerLoginCheck(customer);
	}

	@PostMapping(value = "/customer/add-address/{customerId}")
	public ResponseEntity<CustomerWithAddress> addCustomerAddress(@PathVariable("customerId") Long customerId,
			@RequestBody Address address) {
		log.debug("inside addCustomerAddress");
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
		log.debug("inside updateCustomer");
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
	@GetMapping(value = "/customer/{customerId}")
	public ResponseEntity<Customer> getBasicCustomerDetails(@PathVariable("customerId") Long customerId) {
		log.debug("inside getBasicCustomerDetails");
		return customerServiceProxy.getCustomerById(customerId);
	}

	@GetMapping(value = "/customer/details-with-address/{customerId}")
	public ResponseEntity<CustomerWithAddress> getCustomerDetailsWithAddress(
			@PathVariable("customerId") Long customerId) {
		log.debug("inside getCustomerDetailsWithAddress");
		Customer customer = customerServiceProxy.getCustomerById(customerId).getBody();
		Address address = addressServiceProxy.getAddressById(customerId).getBody();
		if (address != null && customer != null)
			return new ResponseEntity<>(new CustomerWithAddress(customer, address), HttpStatus.OK);
		return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);

	}

	// =============POLICY CATEGORIES & POLICIES============================//
	// ================GET CATEGORIES===========================================//
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

	// ====================GET POLICIES=========================//
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

	@GetMapping(value = "/policy/by-category/{categoryId}")
	public List<Policy> getPoliciesByCategory(@PathVariable("categoryId") Long categoryId) {
		log.debug("inside getPoliciesByCategory");
		PolicyCategory category = categoryServiceProxy.getCategoryById(categoryId).getBody();
		List<Long> policyIds = category.getPolicyIds();

		List<Policy> policies = new ArrayList<>();

		for (Long policyId : policyIds) {
			Policy policy = policyServiceProxy.getPolicyById(policyId);
			policies.add(policy);
		}
		return policies;
	}

	// ==============APPLY & VIEW APPLIED POLICIES=====================//
	@PostMapping(value = "/policy/apply/{policyId}/{customerId}")
	public ResponseEntity<AppliedPolicyWithCustomer> applyPolicy(@PathVariable("policyId") Long policyId,
			@PathVariable("customerId") Long customerId) {
		log.debug("inside applyPolicy: policyId: " + policyId + " customerId: " + customerId);
		IssuePolicy issuedPolicy = policyServiceProxy.addIssuedPolicy(policyId, customerId).getBody();
		Customer customer = customerServiceProxy.getCustomerById(customerId).getBody();
		if (issuedPolicy != null && customer != null)
			return new ResponseEntity<>(new AppliedPolicyWithCustomer(issuedPolicy, customer), HttpStatus.CREATED);
		return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
	}

	@GetMapping(value = "/policy/applied-policies/{customerId}")
	public ResponseEntity<List<PolicyHistory>> policyHistory(@PathVariable("customerId") Long customerId) {
		log.debug("inside policyHistory");
		List<IssuePolicy> issuedPolicies = policyServiceProxy.getIssuedPoliciesByCustomerId(customerId);
		List<PolicyHistory> policyHistoryList = new ArrayList<>();
		for (IssuePolicy issuedPolicy : issuedPolicies) {
			Policy policy = policyServiceProxy.getPolicyById(issuedPolicy.getPolicyId());
			PolicyCategory category = categoryServiceProxy.getCategoryByPolicyId(issuedPolicy.getPolicyId());
			policyHistoryList.add(new PolicyHistory(policy, issuedPolicy, category));
		}
		return new ResponseEntity<>(policyHistoryList, HttpStatus.OK);
	}

	// ==============================QUESTIONS=====================================//
	@PostMapping(value = "/question")
	public ResponseEntity<Question> askQuestion(@RequestBody Question question) {
		log.debug("inside askQuestion");
		return new ResponseEntity<>(questionServiceProxy.addQuestion(question), HttpStatus.CREATED);
	}

	@GetMapping(value = "/question/customer-id/{customerId}")
	public List<Question> questionHistory(@PathVariable("customerId") Long customerId) {
		log.debug("inside questionHistory");
		return questionServiceProxy.getAllQuestionsByCustomerId(customerId);
	}

	// =========================CONTACT FORM================================//

	@PostMapping(value = "/contact-form")
	public ContactForm addContactForm(@RequestBody ContactForm contactForm) {
		log.debug("inside addContactForm");
		return contactFormServiceProxy.addContactForm(contactForm);
	}
}
