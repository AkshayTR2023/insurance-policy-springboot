package com.insurance.service;

import java.util.List;

import com.insurance.entity.ContactForm;

public interface IContactFormService {

	public ContactForm addContactForm(ContactForm contactForm);

	public List<ContactForm> getAllContactForms();

	public ContactForm getContactFormById(Long Id);

	public List<ContactForm> getContactFormByFullName(String fullName);
}
