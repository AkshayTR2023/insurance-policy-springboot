package com.insurance.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.insurance.entity.IssuePolicy;
import com.insurance.repository.IssuePolicyRepository;

@Service(value = "issuePolicyService")
@Scope(value = "singleton")
public class IssuePolicyService implements IIssuePolicyService {

	@Autowired
	@Qualifier(value = "issuePolicyRepository")
	private IssuePolicyRepository issuePolicyRepository;

	@Override
	public IssuePolicy addIssuedPolicy(Long policyId, Long customerId) {
		IssuePolicy newIssuePolicy = new IssuePolicy();
		newIssuePolicy.setPolicyDateTime(LocalDateTime.now());
		newIssuePolicy.setPolicyId(policyId);
		newIssuePolicy.setCustomerId(customerId);
		newIssuePolicy.setPolicyStatus(0);

		return issuePolicyRepository.save(newIssuePolicy);
	}

	@Override
	public IssuePolicy updateIssuedPolicyStatus(Long policyId, Long customerId, int status) {
		IssuePolicy existingPolicy = issuePolicyRepository.findByPolicyIdAndCustomerId(policyId, customerId);
		if (existingPolicy != null) {
			existingPolicy.setPolicyStatus(status);
			return issuePolicyRepository.save(existingPolicy);
		}
		return null;
	}

	@Override
	public List<IssuePolicy> getIssuedPoliciesByPolicyId(Long policyId) {
		return issuePolicyRepository.findByPolicyId(policyId);
	}

	@Override
	public List<IssuePolicy> getIssuedPoliciesByCustomerId(Long customerId) {
		return issuePolicyRepository.findByCustomerId(customerId);
	}

	@Override
	public List<IssuePolicy> getAllIssuedPolicies() {
		return issuePolicyRepository.findAll();
	}

	@Override
	public IssuePolicy getIssuedPolicyById(Long issuePolicyId) {
		return issuePolicyRepository.findById(issuePolicyId).orElse(null);
	}

	@Override
	public void deleteIssuedPolicy(Long issuePolicyId) {
		issuePolicyRepository.deleteById(issuePolicyId);

	}

	@Override
	public void deleteIssuedPolicyByCustomerId(Long customerId) {
		issuePolicyRepository.deleteByCustomerId(customerId);
		
	}

	@Override
	public void deleteIssuedPolicyByPolicyId(Long policyId) {
		issuePolicyRepository.deleteByPolicyId(policyId);
		
	}

}
