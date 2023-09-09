package com.insurance.dto;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Policy {
	private Long policyId;
	private String policyName;
	private BigDecimal policyCoverageAmount;
	private BigDecimal policyPremium;
	private Integer policyCoveragePeriodMonths;
	private BigDecimal policyCoverageLimit;
}
