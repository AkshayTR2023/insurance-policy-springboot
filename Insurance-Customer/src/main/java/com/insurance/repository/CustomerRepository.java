package com.insurance.repository;

import org.springframework.context.annotation.Scope;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.insurance.entity.Customer;

@Repository(value = "customerRepository")
@Scope(value = "singleton")
public interface CustomerRepository extends JpaRepository<Customer, Long> {

	Customer findByCustomerEmailAndCustomerPassword(String customerEmail, String customerPassword);

	Customer findByCustomerEmail(String customerEmail);

}
