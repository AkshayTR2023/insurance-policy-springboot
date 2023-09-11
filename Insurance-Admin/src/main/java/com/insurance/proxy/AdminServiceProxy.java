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
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.insurance.entity.Admin;
import com.insurance.exception.NotFoundException;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;

@FeignClient(name = "admin-service")
public interface AdminServiceProxy {

	@Retry(name = "admin-feign-retry")
	@CircuitBreaker(name = "admin-feign-cb", fallbackMethod = "fallbackForRegisterAdmin")
	@PostMapping(value = "/admin/register", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Admin> registerAdmin(@RequestBody Admin admin);

	@Retry(name = "admin-feign-retry")
	@CircuitBreaker(name = "admin-feign-cb", fallbackMethod = "fallbackForAdminLoginCheck")
	@PostMapping(value = "/admin/login", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Admin> adminLoginCheck(@RequestBody Admin admin);

	@Retry(name = "admin-feign-retry")
	@CircuitBreaker(name = "admin-feign-cb", fallbackMethod = "fallbackForUpdateAdmin")
	@PutMapping(value = "/admin/{adminId}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Admin> updateAdmin(@PathVariable("adminId") Long adminId, @RequestBody Admin admin);

	@Retry(name = "admin-feign-retry")
	@CircuitBreaker(name = "admin-feign-cb", fallbackMethod = "fallbackForGetAllAdmins")
	@GetMapping(value = "/admin", produces = MediaType.APPLICATION_JSON_VALUE)
	public List<Admin> getAllAdmins();

	@Retry(name = "admin-feign-retry")
	@CircuitBreaker(name = "admin-feign-cb", fallbackMethod = "fallbackForGetAdminById")
	@GetMapping(value = "/admin/{adminId}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Admin> getAdminById(@PathVariable("adminId") Long adminId);

	@Retry(name = "admin-feign-retry")
	@CircuitBreaker(name = "admin-feign-cb", fallbackMethod = "fallbackForGetAdminByEmail")
	@GetMapping(value = "/admin/email/{adminEmail}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Admin> getAdminByEmail(@PathVariable("adminEmail") String adminEmail);

	// ==============================FALLBACKS=================================//
	public default ResponseEntity<Admin> fallbackForRegisterAdmin(Admin admin, Throwable cause) {
		if (cause instanceof NotFoundException) {
			throw (NotFoundException) cause;
		}
		System.err.println("Exception: => " + cause.getMessage());
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
				.body(new Admin(0L, "default", "default", "default"));
	}

	public default ResponseEntity<Admin> fallbackForAdminLoginCheck(Admin admin, Throwable cause) {
		if (cause instanceof NotFoundException) {
			throw (NotFoundException) cause;
		}
		System.err.println("Exception: => " + cause.getMessage());
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
				.body(new Admin(0L, "default", "default", "default"));
	}

	public default ResponseEntity<Admin> fallbackForUpdateAdmin(Long adminId, Admin admin, Throwable cause) {
		if (cause instanceof NotFoundException) {
			throw (NotFoundException) cause;
		}
		System.err.println("Exception: => " + cause.getMessage());
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
				.body(new Admin(adminId, "default", "default", "default"));
	}

	public default List<Admin> fallbackForGetAllAdmins(Throwable cause) {
		if (cause instanceof NotFoundException) {
			throw (NotFoundException) cause;
		}
		System.err.println("Exception: => " + cause.getMessage());
		return Collections.emptyList();
	}

	public default ResponseEntity<Admin> fallbackForGetAdminById(Long adminId, Throwable cause) {
		if (cause instanceof NotFoundException) {
			throw (NotFoundException) cause;
		}
		System.err.println("Exception: => " + cause.getMessage());
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
				.body(new Admin(adminId, "default", "default", "default"));
	}

	public default ResponseEntity<Admin> fallbackForGetAdminByEmail(String adminEmail, Throwable cause) {
		if (cause instanceof NotFoundException) {
			throw (NotFoundException) cause;
		}
		System.err.println("Exception: => " + cause.getMessage());
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
				.body(new Admin(0L, adminEmail, "default", "default"));
	}

}
