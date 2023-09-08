package com.insurance.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

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
	public IssuePolicy addIssuedPolicy(Long policyId, Long userId) {
		IssuePolicy newIssuePolicy = new IssuePolicy();
		newIssuePolicy.setPolicyDateTime(LocalDateTime.now());
		newIssuePolicy.setPolicyId(policyId);
		newIssuePolicy.setUserId(userId);
		newIssuePolicy.setPolicyStatus(0);

		return issuePolicyRepository.save(newIssuePolicy);
	}

	@Override
	public IssuePolicy updateIssuedPolicyStatus(Long policyId, Long userId, int status) {
		IssuePolicy existingPolicy = issuePolicyRepository.findByPolicyIdAndUserId(policyId, userId);
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
	public List<IssuePolicy> getIssuedPoliciesByUserId(Long userId) {
		return issuePolicyRepository.findByUserId(userId);
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

}
