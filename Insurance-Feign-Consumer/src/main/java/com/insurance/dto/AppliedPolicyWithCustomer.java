package com.insurance.dto;

import java.time.LocalDateTime;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class AppliedPolicyWithCustomer {
	private Long issuePolicyId;

	private Long policyId;
	private Long customerId;

	private LocalDateTime policyDateTime;
	private int policyStatus;

	private Customer customer;

	public AppliedPolicyWithCustomer(IssuePolicy issuePolicy, Customer customer) {
		this.issuePolicyId = issuePolicy.getIssuePolicyId();
		this.policyId = issuePolicy.getPolicyId();
		this.customerId = issuePolicy.getCustomerId();
		this.policyDateTime = issuePolicy.getPolicyDateTime();
		this.policyStatus = issuePolicy.getPolicyStatus();
		this.customer = customer;
	}

}
