package com.insurance.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.insurance.dto.Policy;
import com.insurance.proxy.PolicyCategoryServiceProxy;
import com.insurance.proxy.PolicyServiceProxy;


@RestController(value = "policyController")
@Scope(value = "request")
@RequestMapping("/policy")
public class PolicyConsumerRestController {

	@Autowired
	private PolicyServiceProxy policyFeignProxy; 
	
	@Autowired
	private PolicyCategoryServiceProxy categoryFeignProxy; 
	
	private Logger log = LoggerFactory.getLogger(PolicyConsumerRestController.class);

	//==============================POST=================================//
		@PostMapping(value = "/category-id/{categoryId}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
		@ResponseStatus(value = HttpStatus.CREATED)
		public ResponseEntity<Policy> createPolicy(@PathVariable("categoryId")Long categoryId,  @RequestBody Policy policy) {
			log.debug("inside addPolicy");
			Policy addedPolicy = policyFeignProxy.createPolicy(policy).getBody();
			log.debug(""+addedPolicy.getPolicyId());
			categoryFeignProxy.addPolicyIdToCategory(categoryId, addedPolicy.getPolicyId());
			return ResponseEntity.status(HttpStatus.CREATED).body(addedPolicy);
		}
		
		//==============================PUT=================================//
		@PutMapping(value="/{policyId}",produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
		 public ResponseEntity<String> updatePolicy(@PathVariable Long policyId, @RequestBody Policy updatedPolicy) {
			log.debug("inside updatePolicy");
			String result = policyFeignProxy.updatePolicy(policyId, updatedPolicy).getBody();
	        
	        if (result.equals("Policy with ID " + policyId + " updated successfully")) {
	            return ResponseEntity.ok(result);
	        } else {
	            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(result);
	        }
	    }
		//==============================GET=================================//
		@GetMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
		public List<Policy> getAllPolicies() {
			log.debug("inside getAllPolicies");
			return policyFeignProxy.getAllPolicies();
		}

		@GetMapping(value = "/{policyId}", produces = MediaType.APPLICATION_JSON_VALUE)
		public Policy getPolicyById(@PathVariable("policyId") Long policyId) {
			log.debug("inside getPolicyById");
			return policyFeignProxy.getPolicyById(policyId);
		}

		@GetMapping(value = "/name-like/{namePhrase}", produces = MediaType.APPLICATION_JSON_VALUE)
		public List<Policy> getAllPoliciesByNameLike(@PathVariable("namePhrase") String namePhrase) {
			log.debug("inside getAllPoliciesByNameLike");
			return policyFeignProxy.getAllPoliciesByNameLike(namePhrase);
		}
		
		@GetMapping(value = "/name/{policyName}", produces = MediaType.APPLICATION_JSON_VALUE)
		public Policy getPolicyByName(@PathVariable("policyName") String policyName) {
			log.debug("inside getPolicyByName");
			return policyFeignProxy.getPolicyByName(policyName);
		}

		// ==============================DEL=================================//

		@DeleteMapping(value = "/{policyId}")
		@ResponseStatus(value = HttpStatus.NO_CONTENT)
		public void deletePolicyById(@PathVariable("policyId") Long policyId) {
			log.debug("inside deletePolicyById");
			categoryFeignProxy.removePolicyIdFromCategories(policyId);
			policyFeignProxy.deletePolicyById(policyId);
		}
}
