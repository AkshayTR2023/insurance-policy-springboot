package com.insurance.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class IssuePolicy {
	private Long issuePolicyId;

	private Long policyId;
	private Long userId;

	private LocalDateTime policyDateTime;
	private int policyStatus;
	/*
	 * 0 ==> Pending
	 * 1 ==> Approved
	 * 2 ==> Rejected
	 */
}