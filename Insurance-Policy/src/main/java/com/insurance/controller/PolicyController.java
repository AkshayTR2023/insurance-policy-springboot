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

import com.insurance.entity.Policy;
import com.insurance.service.IPolicyService;

@RestController(value = "policyController")
@Scope(value = "request")
@RequestMapping("/policy")
@CrossOrigin(origins = "*")
public class PolicyController {
	@Autowired
	@Qualifier(value = "policyService")
	private IPolicyService policyService;
	
	//==============================POST=================================//
	@PostMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(value = HttpStatus.CREATED)
	public ResponseEntity<Policy> createPolicy(@RequestBody Policy policy) {
		return ResponseEntity.status(HttpStatus.CREATED).body(policyService.createPolicy(policy));
	}
	
	//==============================PUT=================================//
	@PutMapping(value="/{policyId}",produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	 public ResponseEntity<String> updatePolicy(@PathVariable Long policyId, @RequestBody Policy updatedPolicy) {
        String result = policyService.updatePolicy(policyId, updatedPolicy);
        
        if (result.equals("Policy with ID " + policyId + " updated successfully")) {
            return ResponseEntity.ok(result);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(result);
        }
    }
	//==============================GET=================================//
	@GetMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
	public List<Policy> getAllPolicies() {
		return policyService.getAllPolicies();
	}

	@GetMapping(value = "/{policyId}", produces = MediaType.APPLICATION_JSON_VALUE)
	public Policy getPolicyById(@PathVariable("policyId") Long policyId) {
		return policyService.getPolicyById(policyId);
	}

	@GetMapping(value = "/name-like/{namePhrase}", produces = MediaType.APPLICATION_JSON_VALUE)
	public List<Policy> getAllPoliciesByNameLike(@PathVariable("namePhrase") String namePhrase) {
		return policyService.getAllPoliciesByNameLike(namePhrase);
	}
	
	@GetMapping(value = "/name/{policyName}", produces = MediaType.APPLICATION_JSON_VALUE)
	public Policy getPolicyByName(@PathVariable("policyName") String policyName) {
		return policyService.getPolicyByName(policyName);
	}

	// ==============================DEL=================================//

	@DeleteMapping(value = "/{policyId}")
	@ResponseStatus(value = HttpStatus.NO_CONTENT)
	public void deletePolicyById(@PathVariable("policyId") Long policyId) {
		policyService.deletePolicy(policyId);
	}
}
