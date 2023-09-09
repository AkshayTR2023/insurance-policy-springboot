package com.insurance.repository;

import org.springframework.context.annotation.Scope;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.insurance.entity.Admin;

@Repository(value = "adminRepository")
@Scope(value = "singleton")
public interface AdminRepository extends JpaRepository<Admin, Long> {

	Admin findByAdminEmailAndAdminPassword(String adminEmail, String adminPassword);

	Admin findByAdminEmail(String adminEmail);


}
