package com.insurance.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Admin {

	private Long adminId;
	private String adminEmail;
	private String adminName;
	private String adminPassword;
}
