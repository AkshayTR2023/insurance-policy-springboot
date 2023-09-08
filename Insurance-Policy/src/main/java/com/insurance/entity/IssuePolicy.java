package com.insurance.entity;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class IssuePolicy {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
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