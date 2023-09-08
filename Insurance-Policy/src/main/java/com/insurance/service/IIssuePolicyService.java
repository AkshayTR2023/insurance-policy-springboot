package com.insurance.service;

import java.util.List;

import com.insurance.entity.IssuePolicy;

public interface IIssuePolicyService {

	public IssuePolicy addIssuedPolicy(Long policyId, Long userId);
	
	public IssuePolicy updateIssuedPolicyStatus(Long policyId, Long userId,int status);
	
	public List<IssuePolicy> getIssuedPoliciesByPolicyId(Long policyId);
	public List<IssuePolicy> getIssuedPoliciesByUserId(Long userId);
	public List<IssuePolicy> getAllIssuedPolicies();
	public IssuePolicy getIssuedPolicyById(Long issuePolicyId);
	
	public void deleteIssuedPolicy(Long issuePolicyId);
}
