package com.email.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.email.DTO.EmailRequestDTO;

@Service
public class EmailSenderService {

	@Autowired
	private JavaMailSender mailSender;

	public String sendEmail(EmailRequestDTO emailRequestDTO) {
		SimpleMailMessage message = new SimpleMailMessage();
		message.setFrom(emailRequestDTO.getFromEmail());
		message.setTo(emailRequestDTO.getToEmail());
		message.setText(emailRequestDTO.getBody());
		message.setSubject(emailRequestDTO.getBody());

		mailSender.send(message);
		return ("Mail Sent Succesfully to: " + emailRequestDTO.getToEmail());

	}
}
