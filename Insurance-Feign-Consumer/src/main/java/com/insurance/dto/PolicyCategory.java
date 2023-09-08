package com.insurance.dto;

import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PolicyCategory {
	private Long categoryId;
	private String categoryName;
	private List<Long> policyIds = new ArrayList<>();

}
