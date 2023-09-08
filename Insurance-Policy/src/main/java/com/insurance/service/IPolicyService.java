package com.insurance.service;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.insurance.entity.Policy;

public interface IPolicyService {

	public Policy createPolicy(Policy policy);
	
	public String updatePolicy(Long policyId,Policy updatedPolicy);
	
	public List<Policy> getAllPolicies();
	public List<Policy> getAllPoliciesByNameLike(String namePhrase);
	public Policy getPolicyById(Long policyId);
	public Policy getPolicyByName(String name);
	
	public ResponseEntity<String> deletePolicy(long policyId);
}
