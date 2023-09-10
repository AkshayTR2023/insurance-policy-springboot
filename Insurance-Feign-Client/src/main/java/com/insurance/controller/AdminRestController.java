package com.insurance.controller;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.insurance.dto.Address;
import com.insurance.dto.Admin;
import com.insurance.dto.AppliedPolicyWithCustomer;
import com.insurance.dto.ContactForm;
import com.insurance.dto.Customer;
import com.insurance.dto.CustomerWithAddress;
import com.insurance.dto.IssuePolicy;
import com.insurance.dto.Policy;
import com.insurance.dto.PolicyCategory;
import com.insurance.dto.Question;
import com.insurance.dto.QuestionWithCustomer;
import com.insurance.proxy.AddressServiceProxy;
import com.insurance.proxy.AdminServiceProxy;
import com.insurance.proxy.ContactFormServiceProxy;
import com.insurance.proxy.CustomerServiceProxy;
import com.insurance.proxy.PolicyCategoryServiceProxy;
import com.insurance.proxy.PolicyServiceProxy;
import com.insurance.proxy.QuestionServiceProxy;

@RestController(value = "adminRestController")
@Scope(value = "request")
@RequestMapping("/admin-feign")
@CrossOrigin(origins = "*")
public class AdminRestController {

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

	private Logger log = LoggerFactory.getLogger(AdminRestController.class);

	// =============================ADMIN======================================//

	// ==============================POST=================================//
	@PostMapping(value = "/admin/register")
	public ResponseEntity<Admin> registerAdmin(@RequestBody Admin admin) {
		log.debug("inside registerAdmin");
		return adminServiceProxy.registerAdmin(admin);
	}

	@PostMapping(value = "/admin/login")
	public ResponseEntity<Admin> checkAdminLogin(@RequestBody Admin admin) {
		log.debug("inside checkAdminLogin");
		return adminServiceProxy.adminLoginCheck(admin);
	}

	// ==============================PUT=================================//

	@PutMapping(value = "/admin/update/{adminId}")
	public ResponseEntity<Admin> updateAdmin(@PathVariable("adminId") Long adminId, @RequestBody Admin admin) {
		log.debug("inside updateAdmin");
		return adminServiceProxy.updateAdmin(adminId, admin);
	}

	// ==============================GET=================================//
	@GetMapping(value = "/admin")
	public List<Admin> getAllAdmins() {
		log.debug("inside getAllAdmins");
		return adminServiceProxy.getAllAdmins();
	}

	@GetMapping(value = "/admin/{adminId}")
	public ResponseEntity<Admin> getAdminById(@PathVariable("adminId") Long adminId) {
		log.debug("inside getAdminById");
		return adminServiceProxy.getAdminById(adminId);
	}

	@GetMapping(value = "/admin/email/{email}")
	public ResponseEntity<Admin> getAdminByEmail(@PathVariable("email") String email) {
		log.debug("inside getAdminByEmail");
		return adminServiceProxy.getAdminByEmail(email);
	}

	// =============================CATEGORY======================================//

	// ==============================POST=================================//
	@PostMapping(value = "/category")
	public PolicyCategory addCategory(@RequestBody PolicyCategory category) {
		log.debug("inside addCategory");
		return categoryServiceProxy.addCategory(category);
	}

	// ==============================PUT=================================//
	@PutMapping(value = "/category/update-name/{categoryId}/{name}")
	public ResponseEntity<PolicyCategory> updateCategoryName(@PathVariable("categoryId") Long categoryId,
			@PathVariable("name") String categoryName) {
		log.debug("inside updateCategoryName");
		return categoryServiceProxy.updateCategoryName(categoryId, categoryName);
	}

	// ==============================GET=================================//
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

	// ==============================DEL=================================//
	@DeleteMapping(value = "/category/{categoryId}")
	public void deleteCategoryById(@PathVariable("categoryId") Long categoryId) {
		log.debug("inside deleteCategoryById");
		PolicyCategory category = categoryServiceProxy.getCategoryById(categoryId).getBody();
		List<Long> policyIds = category.getPolicyIds();
		for (Long policyId : policyIds) {
			log.debug("inside deleting Policies under category : policyId = " + policyId);
			policyServiceProxy.deleteIssuedPolicyByPolicyId(policyId);
			policyServiceProxy.deletePolicyById(policyId);
		}
		categoryServiceProxy.deleteCategoryById(categoryId);
	}

	// =============================POLICY======================================//

	// ==============================POST=================================//
	@PostMapping(value = "/policy/category-id/{categoryId}")
	public ResponseEntity<Policy> createPolicy(@PathVariable("categoryId") Long categoryId,
			@RequestBody Policy policy) {
		log.debug("inside addPolicy");

		Policy createdPolicy = policyServiceProxy.createPolicy(policy).getBody();
		categoryServiceProxy.addPolicyIdToCategory(categoryId, createdPolicy.getPolicyId());
		return new ResponseEntity<>(createdPolicy, HttpStatus.CREATED);
	}

	// ==============================PUT=================================//
	@PutMapping(value = "/policy/{policyId}")
	public ResponseEntity<String> updatePolicy(@PathVariable Long policyId, @RequestBody Policy updatedPolicy) {
		log.debug("inside updatePolicy");
		return policyServiceProxy.updatePolicy(policyId, updatedPolicy);
	}

	// ==============================GET=================================//
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

	@GetMapping(value = "/policy/name/{policyName}")
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
	// ==============================DEL=================================//

	@DeleteMapping(value = "/policy/{policyId}")
	public void deletePolicyById(@PathVariable("policyId") Long policyId) {
		log.debug("inside deletePolicyById");
		categoryServiceProxy.removePolicyIdFromCategories(policyId);
		policyServiceProxy.deleteIssuedPolicyByPolicyId(policyId);
		policyServiceProxy.deletePolicyById(policyId);
	}

	// =============================CUSTOMER - GENERAL
	// =================================//

	// ==============================PUT=================================//
	@PutMapping(value = "/customer/{customerId}")
	public CustomerWithAddress updateCustomerWithAddress(@PathVariable("customerId") Long customerId,
			@RequestBody CustomerWithAddress customerWithAddress) {
		log.debug("inside updateCustomerWithAddress");
		Customer customer = customerServiceProxy
				.updateCustomer(customerId,
						new Customer(customerId, customerWithAddress.getCustomerEmail(),
								customerWithAddress.getCustomerName(), customerWithAddress.getCustomerPassword(),
								customerWithAddress.getCustomerAge(), customerWithAddress.getCustomerGender(),
								customerWithAddress.getCustomerMobile(), customerWithAddress.getCustomerImageUrl()))
				.getBody();
		Address address = addressServiceProxy.updateAddress(customerId, customerWithAddress.getCustomerAddress())
				.getBody();
		return new CustomerWithAddress(customer, address);
	}

	// ==============================GET=================================//
	@GetMapping(value = "/customer/{customerId}")
	public CustomerWithAddress getCustomerWithAddress(@PathVariable("customerId") Long customerId) {
		log.debug("inside getCustomerWithAddress");

		Customer customer = customerServiceProxy.getCustomerById(customerId).getBody();
		Address address = addressServiceProxy.getAddressById(customerId).getBody();
		return new CustomerWithAddress(customer, address);
	}

	@GetMapping(value = "/customer/email/{customerEmail}")
	public CustomerWithAddress getCustomerWithAddressByEmail(@PathVariable("customerEmail") String customerEmail) {
		log.debug("inside getCustomerWithAddressByEmail");
		Customer customer = customerServiceProxy.getCustomerByEmail(customerEmail).getBody();
		Address address = addressServiceProxy.getAddressById(customer.getCustomerId()).getBody();
		return new CustomerWithAddress(customer, address);
	}

	@GetMapping(value = "/customer")
	public List<Customer> getAllCustomers() {
		log.debug("inside getAllCustomers");
		return customerServiceProxy.getAllCustomers();
	}

	@GetMapping(value = "/customer/with-address")
	public List<CustomerWithAddress> getAllCustomersWithAddress() {
		log.debug("inside getAllCustomersWithAddress");
		List<Customer> customers = customerServiceProxy.getAllCustomers();
		List<Address> addresses = addressServiceProxy.getAllAddresses();

		List<CustomerWithAddress> customersWithAddress = new ArrayList<>();

		for (Customer customer : customers) {
			Address customerAddress = findAddressByCustomerId(customer.getCustomerId(), addresses);
			if (customerAddress != null) {
				CustomerWithAddress customerWithAddress = new CustomerWithAddress(customer, customerAddress);
				customersWithAddress.add(customerWithAddress);
			}
		}
		return customersWithAddress;
	}

	// Helper method
	private Address findAddressByCustomerId(Long customerId, List<Address> addresses) {
		for (Address address : addresses) {
			if (address.getAddressId().equals(customerId)) {
				return address;
			}
		}
		return null;
	}

	// ==============================DEL=================================//
	@DeleteMapping(value = "/customer/{customerId}")
	public ResponseEntity<Void> deleteCustomerById(@PathVariable("customerId") Long customerId) {
		log.debug("inside deleteCustomerById");
		addressServiceProxy.deleteAddressById(customerId);
		policyServiceProxy.deleteIssuedPolicyByCustomerId(customerId);
		return customerServiceProxy.deleteCustomerById(customerId);
	}

	// ============================CUSTOMER-POLICY=========================================//
	// ==============================PUT=================================//
	@PutMapping(value = "/applied-policies/set-status")
	public AppliedPolicyWithCustomer setAppliedPolicyStatus(
			@RequestBody AppliedPolicyWithCustomer appliedPolicyWithCustomer) {
		log.debug("inside setAppliedPolicyStatus");
		int status = 0;
		if (appliedPolicyWithCustomer.getPolicyStatus().equals("APPROVED"))
			status = 1;
		else if (appliedPolicyWithCustomer.getPolicyStatus().equals("DISAPPROVED"))
			status = 2;
		IssuePolicy updatedIssuePolicy = policyServiceProxy.updatePolicyStatus(appliedPolicyWithCustomer.getPolicyId(),
				appliedPolicyWithCustomer.getCustomerId(), status).getBody();
		Customer customer = customerServiceProxy.getCustomerById(appliedPolicyWithCustomer.getCustomerId()).getBody();
		return new AppliedPolicyWithCustomer(updatedIssuePolicy, customer);
	}

	// ==============================GET=================================//
	@GetMapping(value = "/applied-policies/all")
	public List<AppliedPolicyWithCustomer> getAllAppliedPolicies() {
		log.debug("inside getAllAppliedPolicies");
		List<IssuePolicy> issuePolicies = policyServiceProxy.getAllIssuedPolicies();
		List<Customer> customers = customerServiceProxy.getAllCustomers();
		List<AppliedPolicyWithCustomer> appliedPolicies = new ArrayList<>();

		for (IssuePolicy issuePolicy : issuePolicies) {
			Customer customer = findCustomerByIdMatch(issuePolicy.getCustomerId(), customers);
			if (customer != null) {
				appliedPolicies.add(new AppliedPolicyWithCustomer(issuePolicy, customer));
			}
		}
		return appliedPolicies;
	}

	@GetMapping(value = "/applied-policies/pending")
	public List<AppliedPolicyWithCustomer> getPendingAppliedPolicies() {
		log.debug("inside getPendingAppliedPolicies");
		List<IssuePolicy> issuePolicies = policyServiceProxy.getAllIssuedPolicies();
		List<Customer> customers = customerServiceProxy.getAllCustomers();
		List<AppliedPolicyWithCustomer> appliedPolicies = new ArrayList<>();

		for (IssuePolicy issuePolicy : issuePolicies) {
			Customer customer = findCustomerByIdMatch(issuePolicy.getCustomerId(), customers);
			if (customer != null && issuePolicy.getPolicyStatus() == 0) {
				appliedPolicies.add(new AppliedPolicyWithCustomer(issuePolicy, customer));
			}
		}
		return appliedPolicies;
	}

	@GetMapping(value = "/applied-policies/approved")
	public List<AppliedPolicyWithCustomer> getApprovedAppliedPolicies() {
		log.debug("inside getApprovedAppliedPolicies");
		List<IssuePolicy> issuePolicies = policyServiceProxy.getAllIssuedPolicies();
		List<Customer> customers = customerServiceProxy.getAllCustomers();
		List<AppliedPolicyWithCustomer> appliedPolicies = new ArrayList<>();

		for (IssuePolicy issuePolicy : issuePolicies) {
			Customer customer = findCustomerByIdMatch(issuePolicy.getCustomerId(), customers);
			if (customer != null && issuePolicy.getPolicyStatus() == 1) {
				appliedPolicies.add(new AppliedPolicyWithCustomer(issuePolicy, customer));
			}
		}
		return appliedPolicies;
	}

	@GetMapping(value = "/applied-policies/disapproved")
	public List<AppliedPolicyWithCustomer> getDisapprovedAppliedPolicies() {
		log.debug("inside getDisapprovedAppliedPolicies");
		List<IssuePolicy> issuePolicies = policyServiceProxy.getAllIssuedPolicies();
		List<Customer> customers = customerServiceProxy.getAllCustomers();
		List<AppliedPolicyWithCustomer> appliedPolicies = new ArrayList<>();

		for (IssuePolicy issuePolicy : issuePolicies) {
			Customer customer = findCustomerByIdMatch(issuePolicy.getCustomerId(), customers);
			if (customer != null && issuePolicy.getPolicyStatus() == 2) {
				appliedPolicies.add(new AppliedPolicyWithCustomer(issuePolicy, customer));
			}
		}
		return appliedPolicies;
	}

	// Helper method
	private Customer findCustomerByIdMatch(Long id, List<Customer> customers) {
		for (Customer customer : customers) {
			if (customer.getCustomerId().equals(id)) {
				return customer;
			}
		}
		return null;
	}

	// ============================CUSTOMER-QUESTIONS=========================================//
	// ==============================PUT=================================//
	@PutMapping(value = "/question")
	public QuestionWithCustomer answerCustomerQuestion(@RequestBody QuestionWithCustomer questionWithCustomer) {
		log.debug("inside answerCustomerQuestion");
		Question question = questionServiceProxy.answerQuestion(questionWithCustomer.getQuestionId(),
				new Question(questionWithCustomer.getQuestionId(), questionWithCustomer.getQuestion(),
						questionWithCustomer.getAnswer(), questionWithCustomer.getCustomerId()))
				.getBody();
		Customer customer = customerServiceProxy.getCustomerById(questionWithCustomer.getCustomerId()).getBody();
		return new QuestionWithCustomer(question, customer);
	}

	// ==============================GET=================================//
	@GetMapping(value = "/question")
	public List<QuestionWithCustomer> getAllQuestions() {
		log.debug("inside getAllQuestions");
		List<Question> questions = questionServiceProxy.getAllQuestions();
		List<Customer> customers = customerServiceProxy.getAllCustomers();
		List<QuestionWithCustomer> questionsWithCustomers = new ArrayList<>();

		for (Question question : questions) {
			Customer customer = findCustomerByIdMatch(question.getCustomerId(), customers);
			if (customer != null) {
				questionsWithCustomers.add(new QuestionWithCustomer(question, customer));
			}
		}
		return questionsWithCustomers;
	}

	@GetMapping(value = "/question/customer-id/{customerId}")
	public List<QuestionWithCustomer> getAllQuestionsByCustomerId(@PathVariable("customerId") Long CustomerId) {
		log.debug("inside getAllQuestionsByCustomerId");
		List<Question> questions = questionServiceProxy.getAllQuestionsByCustomerId(CustomerId);
		Customer customer = customerServiceProxy.getCustomerById(CustomerId).getBody();
		List<QuestionWithCustomer> questionsWithCustomers = new ArrayList<>();

		for (Question question : questions) {
			questionsWithCustomers.add(new QuestionWithCustomer(question, customer));
		}
		return questionsWithCustomers;
	}

	// ============================CONTACT-FORM=========================================//
	// ==============================GET=================================//
	@GetMapping(value = "/contact-form")
	public List<ContactForm> getAllContactForms() {
		log.debug("inside getAllContactForms");
		return contactFormServiceProxy.getAllContactForms();
	}

	@GetMapping(value = "/contact-form/{contactFormId}")
	public ContactForm getContactFormById(@PathVariable("contactFormId") Long contactFormId) {
		log.debug("inside getContactFormById");
		return contactFormServiceProxy.getContactFormById(contactFormId).getBody();
	}

	@GetMapping(value = "/contact-form/name/{name}")
	public List<ContactForm> getContactFormsByName(@PathVariable("name") String name) {
		log.debug("inside getContactFormsByName");
		return contactFormServiceProxy.getContactFormsByName(name).getBody();
	}

}
