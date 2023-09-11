package com.insurance.dto;

import java.time.LocalDateTime;

import com.insurance.entity.Customer;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class AppliedPolicyWithCustomer {
	private Long issuePolicyId;

	private Long policyId;
	private Long customerId;

	private LocalDateTime policyDateTime;
	private String policyStatus;

	private Customer customer;

	public AppliedPolicyWithCustomer(IssuePolicy issuePolicy, Customer customer) {
		this.issuePolicyId = issuePolicy.getIssuePolicyId();
		this.policyId = issuePolicy.getPolicyId();
		this.customerId = issuePolicy.getCustomerId();
		this.policyDateTime = issuePolicy.getPolicyDateTime();
		if(issuePolicy.getPolicyStatus()==0)
			this.policyStatus="PENDING";
		else if(issuePolicy.getPolicyStatus()==1)
			this.policyStatus="APPROVED";
		else
			this.policyStatus="DISAPPROVED";
		this.customer = customer;
	}

}
