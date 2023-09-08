package com.insurance.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.insurance.entity.Policy;
import com.insurance.repository.PolicyRepository;

@Service(value = "policyService")
@Scope(value = "singleton")
public class PolicyService implements IPolicyService {

	@Autowired
	@Qualifier(value = "policyRepository")
	private PolicyRepository policyRepository;

	@Override
	public Policy createPolicy(Policy policy) {
		return policyRepository.save(policy);
	}

	@Override
	public String updatePolicy(Long policyId,Policy updatedPolicy) {
		 Optional<Policy> existingPolicyOptional = policyRepository.findById(policyId);
	        if (existingPolicyOptional.isPresent()) {
	            Policy existingPolicy = existingPolicyOptional.get();
	            existingPolicy.setPolicyName(updatedPolicy.getPolicyName());
	            existingPolicy.setPolicyCoverageAmount(updatedPolicy.getPolicyCoverageAmount());
	            existingPolicy.setPolicyPremium(updatedPolicy.getPolicyPremium());
	            existingPolicy.setPolicyCoveragePeriodMonths(updatedPolicy.getPolicyCoveragePeriodMonths());
	            existingPolicy.setPolicyCoverageLimit(updatedPolicy.getPolicyCoverageLimit());
	          
	            policyRepository.save(existingPolicy);
	            return "Policy with ID " + policyId + " updated successfully";
	        } else {
	            return "Policy with ID " + policyId + " not found";
	        }
	}

	@Override
	public List<Policy> getAllPolicies() {
		return policyRepository.findAll();
	}

	@Override
	public List<Policy> getAllPoliciesByNameLike(String namePhrase) {
		return policyRepository.findByPolicyNameIgnoreCaseLike("%" + namePhrase + "%");
	}

	@Override
	public Policy getPolicyById(Long policyId) {
		return policyRepository.findById(policyId).orElse(null);
	}

	@Override
	public Policy getPolicyByName(String name) {
		return policyRepository.findByPolicyName(name);
	}

	@Override
	public ResponseEntity<String> deletePolicy(long policyId) {
		Optional<Policy> existingPolicy = policyRepository.findById(policyId);
		if (existingPolicy.isPresent()) {
			policyRepository.deleteById(policyId);
			return ResponseEntity.ok("Policy with ID " + policyId + " deleted successfully");
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Policy with ID " + policyId + " not found");
		}
	}
}
