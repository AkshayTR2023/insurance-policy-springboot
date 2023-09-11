package com.insurance.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmailRequest {
	private String fromEmail;
	private String toEmail;
	private String subject;
	private String body;
}
