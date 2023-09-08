package com.insurance.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.insurance.entity.ContactForm;
import com.insurance.repository.ContactFormRepository;

@Service(value = "contactFormService")
@Scope(value = "singleton")
public class ContactFormService implements IContactFormService{

	@Autowired
	@Qualifier(value = "contactFormRepository")
	ContactFormRepository contactFormRepository;

	@Override
	public ContactForm addContactForm(ContactForm contactForm) {
		return contactFormRepository.save(contactForm);
	}

	@Override
	public List<ContactForm> getAllContactForms() {
		return contactFormRepository.findAll();
	}

	@Override
	public ContactForm getContactFormById(Long Id) {
		return contactFormRepository.findById(Id).orElse(null);
	}

	@Override
	public List<ContactForm> getContactFormByFullName(String fullName) {
		return contactFormRepository.findByFullName(fullName);
	}

}
