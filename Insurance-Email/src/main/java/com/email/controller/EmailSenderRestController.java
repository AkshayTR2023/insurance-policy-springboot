package com.email.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.email.DTO.EmailRequestDTO;
import com.email.service.EmailSenderService;

@RestController
@RequestMapping("/email")
public class EmailSenderRestController {

	@Autowired
	private EmailSenderService emailSenderService;

	@PostMapping("/send")
	public ResponseEntity<String> sendEmail(@RequestBody EmailRequestDTO emailRequestDTO) {
		try {
			String returnMessage = emailSenderService.sendEmail(emailRequestDTO);
			return new ResponseEntity<>(returnMessage,HttpStatus.CREATED); 
		} catch (Exception e) {
			return new ResponseEntity<>("Error sending email: " + e.getMessage(),HttpStatus.BAD_REQUEST);
		}
	}
}