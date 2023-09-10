package com.insurance.service;

import java.util.List;

import com.insurance.entity.IssuePolicy;

public interface IIssuePolicyService {

	public IssuePolicy addIssuedPolicy(Long policyId, Long customerId);
	
	public IssuePolicy updateIssuedPolicyStatus(Long policyId, Long customerId,int status);
	
	public List<IssuePolicy> getIssuedPoliciesByPolicyId(Long policyId);
	public List<IssuePolicy> getIssuedPoliciesByCustomerId(Long customerId);
	public List<IssuePolicy> getAllIssuedPolicies();
	public IssuePolicy getIssuedPolicyById(Long issuePolicyId);
	
	public void deleteIssuedPolicy(Long issuePolicyId);

	public void deleteIssuedPolicyByCustomerId(Long customerId);

	public void deleteIssuedPolicyByPolicyId(Long policyId);
}
