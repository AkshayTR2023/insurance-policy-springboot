package com.insurance.proxy;

import java.util.Collections;
import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.insurance.dto.ContactForm;
import com.insurance.exception.NotFoundException;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;

@FeignClient(name = "contact-form-service")
public interface ContactFormServiceProxy {

	@Retry(name = "insurance-feign-retry")
	@CircuitBreaker(name = "insurance-feign-cb", fallbackMethod = "fallbackForAddContactForm")
	@PostMapping(value = "/contactForm", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(value = HttpStatus.CREATED)
	public ContactForm addContactForm(@RequestBody ContactForm contactForm);
	
	@Retry(name = "insurance-feign-retry")
	@CircuitBreaker(name = "insurance-feign-cb", fallbackMethod = "fallbackForGetAllContactForms")
	@GetMapping(value = "/contactForm", produces = MediaType.APPLICATION_JSON_VALUE)
	public List<ContactForm> getAllContactForms();
	
	@Retry(name = "insurance-feign-retry")
	@CircuitBreaker(name = "insurance-feign-cb", fallbackMethod = "fallbackForGetContactFormById")
	@GetMapping(value = "/contactForm/{contactFormId}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ContactForm> getContactFormById(@PathVariable("contactFormId") Long contactFormId);
	
	@Retry(name = "insurance-feign-retry")
	@CircuitBreaker(name = "insurance-feign-cb", fallbackMethod = "fallbackForGetContactFormsByName")
	@GetMapping(value = "/contactForm/name/{fullName}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<ContactForm>> getContactFormsByName(@PathVariable("fullName") String fullName);

	//===================================FALLBACKS================================================//
	public default ContactForm fallbackForAddContactForm(ContactForm contactForm,Throwable cause) {
		if (cause instanceof NotFoundException) {
			throw (NotFoundException) cause;
		}
		System.err.println("Exception: => " + cause.getMessage());
		return new ContactForm(0L, "default", "default","default");
	}
	
	public default List<ContactForm> fallbackForGetAllContactForms(Throwable cause){
		if (cause instanceof NotFoundException) {
			throw (NotFoundException) cause;
		}
		System.err.println("Exception: => " + cause.getMessage());
		return Collections.emptyList();
		
	}
	
	public default ResponseEntity<ContactForm> fallbackForGetContactFormById(Long contactFormId,Throwable cause){
		if (cause instanceof NotFoundException) {
			throw (NotFoundException) cause;
		}
		System.err.println("Exception: => " + cause.getMessage());
		System.err.println("Exception: => " + cause.getMessage());
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
				.body(new ContactForm(0L, "default", "default","default"));
	}
	
	public default ResponseEntity<List<ContactForm>> fallbackForGetContactFormsByName(String fullName, Throwable cause){
		if (cause instanceof NotFoundException) {
			throw (NotFoundException) cause;
		}
		System.err.println("Exception: => " + cause.getMessage());
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
				.body(Collections.emptyList());
		
	}
	
}
