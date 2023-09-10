package com.insurance.proxy;

import java.util.Collections;
import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.insurance.dto.Address;
import com.insurance.exception.NotFoundException;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;

@FeignClient(name = "address-service")
public interface AddressServiceProxy {

	@Retry(name = "insurance-feign-retry")
	@CircuitBreaker(name = "insurance-feign-cb", fallbackMethod = "fallbackForAddAddress")
	@PostMapping(value = "/address/{customerId}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(value = HttpStatus.CREATED)
	public ResponseEntity<Address> addAddress(@PathVariable("customerId") Long customerId, @RequestBody Address address);

	@Retry(name = "insurance-feign-retry")
	@CircuitBreaker(name = "insurance-feign-cb", fallbackMethod = "fallbackForUpdateAddress")
	@PutMapping(value = "/address/{addressId}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Address> updateAddress(@PathVariable("addressId") Long addressId,
			@RequestBody Address address);

	@Retry(name = "insurance-feign-retry")
	@CircuitBreaker(name = "insurance-feign-cb", fallbackMethod = "fallbackForGetAllAddresses")
	@GetMapping(value = "/address", produces = MediaType.APPLICATION_JSON_VALUE)
	public List<Address> getAllAddresses();

	@Retry(name = "insurance-feign-retry")
	@CircuitBreaker(name = "insurance-feign-cb", fallbackMethod = "fallbackForGetAddressById")
	@GetMapping(value = "/address/{addressId}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Address> getAddressById(@PathVariable("addressId") Long addressId);

	@Retry(name = "insurance-feign-retry")
	@CircuitBreaker(name = "insurance-feign-cb", fallbackMethod = "fallbackForDeleteAddressById")
	@DeleteMapping(value = "/address/{addressId}")
	@ResponseStatus(value = HttpStatus.NO_CONTENT)
	public ResponseEntity<Void> deleteAddressById(@PathVariable("addressId") Long addressId);

	// =================================FALLBACKS=====================================//
	
	public default ResponseEntity<Address> fallbackForAddAddress(Long customerId,Address address, Throwable cause){
		if (cause instanceof NotFoundException) {
			throw (NotFoundException) cause;
		}
		System.err.println("Exception: => " + cause.getMessage());
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
				.body(new Address(customerId, "default", "default", "default", "default", "default"));
	}
	public default ResponseEntity<Address> fallbackForUpdateAddress(Long addressId,
			Address address, Throwable cause){
		if (cause instanceof NotFoundException) {
			throw (NotFoundException) cause;
		}
		System.err.println("Exception: => " + cause.getMessage());
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
				.body(new Address(addressId, "default", "default", "default", "default", "default"));
	}

	public default List<Address> fallbackForGetAllAddresses(Throwable cause){
		if (cause instanceof NotFoundException) {
			throw (NotFoundException) cause;
		}
		System.err.println("Exception: => " + cause.getMessage());
		return Collections.emptyList();
	}
	
	public default ResponseEntity<Address> fallbackForGetAddressById(Long addressId, Throwable cause){
		if (cause instanceof NotFoundException) {
			throw (NotFoundException) cause;
		}
		System.err.println("Exception: => " + cause.getMessage());
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
				.body(new Address(addressId, "default", "default", "default", "default", "default"));
	}
	
	public default ResponseEntity<Void> fallbackForDeleteAddressById(Long addressId, Throwable cause){
		if (cause instanceof NotFoundException) {
			throw (NotFoundException) cause;
		}
		System.err.println("Exception: => " + cause.getMessage());
		System.err.println("Error: Delete operation failed for address with ID: " + addressId);
		return ResponseEntity.internalServerError().build();
	}
}
