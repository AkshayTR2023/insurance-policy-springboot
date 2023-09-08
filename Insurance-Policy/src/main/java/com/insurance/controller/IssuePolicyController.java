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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.insurance.entity.IssuePolicy;
import com.insurance.service.IIssuePolicyService;

@RestController(value = "issuePolicyController")
@Scope(value = "request")
@RequestMapping("/issue-policy")
@CrossOrigin(origins = "*")
public class IssuePolicyController {
	@Autowired
	@Qualifier(value = "issuePolicyService")
	private IIssuePolicyService issuePolicyService;

	// ==============================POST=================================//
	@PostMapping(value = "/{policyId}/{userId}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(value = HttpStatus.CREATED)
	public ResponseEntity<IssuePolicy> addIssuedPolicy(@PathVariable("policyId") Long policyId,
			@PathVariable("userId") Long userId) {
		return ResponseEntity.status(HttpStatus.CREATED).body(issuePolicyService.addIssuedPolicy(policyId, userId));
	}

	// ==============================PUT=================================//
	@PutMapping(value="/{policyId}/{userId}/{status}",produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	 public ResponseEntity<IssuePolicy> updatePolicyStatus(@PathVariable("policyId") Long policyId, @PathVariable("userId") Long userId,@PathVariable("status") int status) {
		IssuePolicy updatedIssuedPolicy = issuePolicyService.updateIssuedPolicyStatus(policyId, policyId, status);
	        if (updatedIssuedPolicy != null) {
	            return new ResponseEntity<>(updatedIssuedPolicy, HttpStatus.OK);
	        } else {
	            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	        }
    }

	// ==============================GET=================================//
	@GetMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
	public List<IssuePolicy> getAllIssuedPolicies() {
		return issuePolicyService.getAllIssuedPolicies();
	}

	@GetMapping(value = "/policyId/{policyId}", produces = MediaType.APPLICATION_JSON_VALUE)
	public List<IssuePolicy> getIssuedPoliciesByPolicyId(@PathVariable("policyId") Long policyId) {
		return issuePolicyService.getIssuedPoliciesByPolicyId(policyId);
	}

	@GetMapping(value = "/userId/{userId}", produces = MediaType.APPLICATION_JSON_VALUE)
	public List<IssuePolicy> getIssuedPoliciesByUserId(@PathVariable("userId") Long userId) {
		return issuePolicyService.getIssuedPoliciesByUserId(userId);
	}
	@GetMapping(value = "/id/{issuedPolicyId}", produces = MediaType.APPLICATION_JSON_VALUE)
	public IssuePolicy getIssuedPolicyById(@PathVariable("issuedPolicyId") Long issuedPolicyId) {
		return issuePolicyService.getIssuedPolicyById(issuedPolicyId);
	}
	// ==============================DEL=================================//

	@DeleteMapping(value = "/{issuedPolicyId}")
	@ResponseStatus(value = HttpStatus.NO_CONTENT)
	public void deletepolicyById(@PathVariable("issuedPolicyId") Long issuedPolicyId) {
		issuePolicyService.deleteIssuedPolicy(issuedPolicyId);
	}
}