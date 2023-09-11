package com.email.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor 
@NoArgsConstructor
public class EmailRequestDTO {
	private String fromEmail;
    private String toEmail;
    private String subject;
    private String body;
}