package com.insurance.repository;

import java.util.List;

import org.springframework.context.annotation.Scope;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.insurance.entity.ContactForm;

@Repository(value = "contactFormRepository")
@Scope(value = "singleton")
public interface ContactFormRepository extends JpaRepository<ContactForm, Long> {

	List<ContactForm> findByFullName(String fullName);

}
