package com.insurance.proxy;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.insurance.dto.EmailRequest;
import com.insurance.exception.NotFoundException;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;

@FeignClient(name = "emailnotification-service")
public interface EmailServiceProxy {
	@Retry(name = "admin-feign-retry")
	@CircuitBreaker(name = "admin-feign-cb", fallbackMethod = "fallbackForSendEmail")
	@PostMapping(value = "/email/send")
	@ResponseStatus(value = HttpStatus.CREATED)
	public ResponseEntity<String> sendEmail(@RequestBody EmailRequest emailRequest);

	public default ResponseEntity<String> fallbackForSendEmail(EmailRequest emailRequest, Throwable cause) {
		if (cause instanceof NotFoundException) {
			throw (NotFoundException) cause;
		}
		System.err.println("Exception: => " + cause.getMessage());
		return ResponseEntity.status(HttpStatus.CREATED).body("Mail Sent Succesfully to: "+emailRequest.getToEmail());
	}
}
