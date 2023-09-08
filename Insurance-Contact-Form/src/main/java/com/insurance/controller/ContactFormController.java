package com.insurance.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.insurance.entity.ContactForm;
import com.insurance.service.IContactFormService;

@RestController(value = "contactFormController")
@Scope(value = "request")
@RequestMapping("/contactForm")
@CrossOrigin(origins = "*")
public class ContactFormController {
	@Autowired
	@Qualifier(value = "contactFormService")
	private IContactFormService contactFormService;

	// ==============================POST=================================//
	@PostMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(value = HttpStatus.CREATED)
	public ContactForm addContactForm(@RequestBody ContactForm contactForm) {
		return contactFormService.addContactForm(contactForm);
	}

	// ==============================GET=================================//
	@GetMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
	public List<ContactForm> getAllContactForms() {
		return contactFormService.getAllContactForms();
	}

	@GetMapping(value = "/{contactFormId}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ContactForm> getContactFormById(@PathVariable("contactFormId") Long contactFormId) {
		ContactForm contactForm = contactFormService.getContactFormById(contactFormId);
		if (contactForm != null) {
			return new ResponseEntity<>(contactForm, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@GetMapping(value = "/name/{fullName}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<ContactForm>> getContactFormsByName(@PathVariable("fullName") String fullName) {
		List<ContactForm> contactForms = contactFormService.getContactFormByFullName(fullName);
		if (!contactForms.isEmpty())
			return new ResponseEntity<>(contactForms, HttpStatus.OK);
		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}
}
