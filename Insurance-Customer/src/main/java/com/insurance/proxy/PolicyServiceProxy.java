package com.insurance.proxy;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.insurance.dto.IssuePolicy;
import com.insurance.dto.Policy;
import com.insurance.exception.NotFoundException;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;

@FeignClient(name = "policy-service")
public interface PolicyServiceProxy {

	// ==================POLICY METHODS==========================================//
	@Retry(name = "user-feign-retry")
	@CircuitBreaker(name = "user-feign-cb", fallbackMethod = "fallbackForGetAllPolicies")
	@GetMapping(value = "/policy", produces = MediaType.APPLICATION_JSON_VALUE)
	public List<Policy> getAllPolicies();

	@Retry(name = "user-feign-retry")
	@CircuitBreaker(name = "user-feign-cb", fallbackMethod = "fallbackForGetPolicyById")
	@GetMapping(value = "/policy/{policyId}", produces = MediaType.APPLICATION_JSON_VALUE)
	public Policy getPolicyById(@PathVariable("policyId") Long policyId);

	@Retry(name = "user-feign-retry")
	@CircuitBreaker(name = "user-feign-cb", fallbackMethod = "fallbackForGetAllPoliciesByNameLike")
	@GetMapping(value = "/policy/name-like/{namePhrase}", produces = MediaType.APPLICATION_JSON_VALUE)
	public List<Policy> getAllPoliciesByNameLike(@PathVariable("namePhrase") String namePhrase);

	@Retry(name = "user-feign-retry")
	@CircuitBreaker(name = "user-feign-cb", fallbackMethod = "fallbackForGetPolicyByName")
	@GetMapping(value = "/policy/name/{policyName}", produces = MediaType.APPLICATION_JSON_VALUE)
	public Policy getPolicyByName(@PathVariable("policyName") String policyName);

	// ====================ISSUE POLICY
	// METHODS=======================================//

	@Retry(name = "user-feign-retry")
	@CircuitBreaker(name = "user-feign-cb", fallbackMethod = "fallbackForAddIssuedPolicy")
	@PostMapping(value = "/issue-policy/{policyId}/{customerId}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(value = HttpStatus.CREATED)
	public ResponseEntity<IssuePolicy> addIssuedPolicy(@PathVariable("policyId") Long policyId,
			@PathVariable("customerId") Long customerId);

	@Retry(name = "user-feign-retry")
	@CircuitBreaker(name = "user-feign-cb", fallbackMethod = "fallbackForGetAllIssuedPolicies")
	@GetMapping(value = "/issue-policy", produces = MediaType.APPLICATION_JSON_VALUE)
	public List<IssuePolicy> getAllIssuedPolicies();

	@Retry(name = "user-feign-retry")
	@CircuitBreaker(name = "user-feign-cb", fallbackMethod = "fallbackForGetIssuedPoliciesByPolicyId")
	@GetMapping(value = "/issue-policy/policyId/{policyId}", produces = MediaType.APPLICATION_JSON_VALUE)
	public List<IssuePolicy> getIssuedPoliciesByPolicyId(@PathVariable("policyId") Long policyId);

	@Retry(name = "user-feign-retry")
	@CircuitBreaker(name = "user-feign-cb", fallbackMethod = "fallbackForGetIssuedPoliciesByCustomerId")
	@GetMapping(value = "/issue-policy/customerId/{customerId}", produces = MediaType.APPLICATION_JSON_VALUE)
	public List<IssuePolicy> getIssuedPoliciesByCustomerId(@PathVariable("customerId") Long customerId);

	@Retry(name = "user-feign-retry")
	@CircuitBreaker(name = "user-feign-cb", fallbackMethod = "fallbackForGetIssuedPolicyById")
	@GetMapping(value = "/issue-policy/id/{issuedPolicyId}", produces = MediaType.APPLICATION_JSON_VALUE)
	public IssuePolicy getIssuedPolicyById(@PathVariable("issuedPolicyId") Long issuedPolicyId);

	// =============================FALLBACKS FOR
	// POLICY====================================//
	public default List<Policy> fallbackForGetAllPolicies(Throwable cause) {
		if (cause instanceof NotFoundException) {
			throw (NotFoundException) cause;
		}
		System.err.println("Exception: => " + cause.getMessage());
		return Collections.emptyList();
	}

	public default Policy fallbackForGetPolicyById(Long policyId, Throwable cause) {
		if (cause instanceof NotFoundException) {
			throw (NotFoundException) cause;
		}
		System.err.println("Exception: => " + cause.getMessage());
		return null;
	}

	public default List<Policy> fallbackForGetAllPoliciesByNameLike(String namePhrase, Throwable cause) {
		if (cause instanceof NotFoundException) {
			throw (NotFoundException) cause;
		}
		System.err.println("Exception: => " + cause.getMessage());
		return Collections.emptyList();
	}

	public default Policy fallbackForGetPolicyByName(String policyName, Throwable cause) {
		if (cause instanceof NotFoundException) {
			throw (NotFoundException) cause;
		}
		System.err.println("Exception: => " + cause.getMessage());
		return null;
	}

	// =============================FALLBACKS FOR ISSUE
	// POLICY====================================//
	public default ResponseEntity<IssuePolicy> fallbackForAddIssuedPolicy(Long policyId, Long customerId,
			Throwable cause) {
		if (cause instanceof NotFoundException) {
			throw (NotFoundException) cause;
		}
		System.err.println("Exception: => " + cause.getMessage());
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
				.body(new IssuePolicy(0L, 0L, 0L, LocalDateTime.now(), 0));
	}

	public default List<IssuePolicy> fallbackForGetAllIssuedPolicies(Throwable cause) {
		if (cause instanceof NotFoundException) {
			throw (NotFoundException) cause;
		}
		System.err.println("Exception: => " + cause.getMessage());
		return Collections.emptyList();
	}

	public default List<IssuePolicy> fallbackForGetIssuedPoliciesByPolicyId(Long policyId, Throwable cause) {
		if (cause instanceof NotFoundException) {
			throw (NotFoundException) cause;
		}
		System.err.println("Exception: => " + cause.getMessage());
		return Collections.emptyList();
	}

	public default List<IssuePolicy> fallbackForGetIssuedPoliciesByCustomerId(Long customerId, Throwable cause) {
		if (cause instanceof NotFoundException) {
			throw (NotFoundException) cause;
		}
		System.err.println("Exception: => " + cause.getMessage());
		return Collections.emptyList();
	}

	public default IssuePolicy fallbackForGetIssuedPolicyById(Long issuedPolicyId, Throwable cause) {
		if (cause instanceof NotFoundException) {
			throw (NotFoundException) cause;
		}
		return new IssuePolicy(issuedPolicyId, 0L, 0L, LocalDateTime.now(), 0);
	}
}
