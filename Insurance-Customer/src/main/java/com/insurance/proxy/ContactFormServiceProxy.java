package com.insurance.proxy;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.insurance.dto.ContactForm;
import com.insurance.exception.NotFoundException;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;

@FeignClient(name = "contact-form-service")
public interface ContactFormServiceProxy {

	@Retry(name = "user-feign-retry")
	@CircuitBreaker(name = "user-feign-cb", fallbackMethod = "fallbackForAddContactForm")
	@PostMapping(value = "/contactForm", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(value = HttpStatus.CREATED)
	public ContactForm addContactForm(@RequestBody ContactForm contactForm);
	
	//===================================FALLBACK================================================//
	public default ContactForm fallbackForAddContactForm(ContactForm contactForm,Throwable cause) {
		if (cause instanceof NotFoundException) {
			throw (NotFoundException) cause;
		}
		System.err.println("Exception: => " + cause.getMessage());
		return new ContactForm(0L, "default", "default","default");
	}
	
}
