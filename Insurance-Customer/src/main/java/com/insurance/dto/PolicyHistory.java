package com.insurance.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PolicyHistory {

	Long issueId;
	Long policyId;
	String policyName;
	BigDecimal policyCoverageAmount;
	String categoryName;
	LocalDateTime dateTime;
	String status;

	public PolicyHistory(Policy policy, IssuePolicy issuePolicy, PolicyCategory category) {
		this.issueId = issuePolicy.getIssuePolicyId();
		this.policyId = issuePolicy.getPolicyId();
		this.policyName = policy.getPolicyName();
		this.policyCoverageAmount = policy.getPolicyCoverageAmount();
		this.categoryName = category.getCategoryName();
		if (issuePolicy.getPolicyStatus() == 0)
			this.status = "PENDING";
		else if (issuePolicy.getPolicyStatus() == 1)
			this.status = "APPROVED";
		else
			this.status = "DISAPPROVED";
		this.dateTime=issuePolicy.getPolicyDateTime();
	}
}
