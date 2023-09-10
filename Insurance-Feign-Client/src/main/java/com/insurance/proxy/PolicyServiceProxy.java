package com.insurance.proxy;

import java.math.BigDecimal;
import java.time.LocalDateTime;
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

import com.insurance.dto.IssuePolicy;
import com.insurance.dto.Policy;
import com.insurance.exception.NotFoundException;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;

@FeignClient(name = "policy-service")
public interface PolicyServiceProxy {

	// ==================POLICY METHODS==========================================//
	@Retry(name = "insurance-feign-retry")
	@CircuitBreaker(name = "insurance-feign-cb", fallbackMethod = "fallbackForAddPolicy")
	@PostMapping(value = "/policy", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(value = HttpStatus.CREATED)
	public ResponseEntity<Policy> createPolicy(@RequestBody Policy policy);

	@Retry(name = "insurance-feign-retry")
	@CircuitBreaker(name = "insurance-feign-cb", fallbackMethod = "fallbackForUpdatePolicy")
	@PutMapping(value = "/policy/{policyId}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> updatePolicy(@PathVariable Long policyId, @RequestBody Policy updatedPolicy);

	@Retry(name = "insurance-feign-retry")
	@CircuitBreaker(name = "insurance-feign-cb", fallbackMethod = "fallbackForGetAllPolicies")
	@GetMapping(value = "/policy", produces = MediaType.APPLICATION_JSON_VALUE)
	public List<Policy> getAllPolicies();

	@Retry(name = "insurance-feign-retry")
	@CircuitBreaker(name = "insurance-feign-cb", fallbackMethod = "fallbackForGetPolicyById")
	@GetMapping(value = "/policy/{policyId}", produces = MediaType.APPLICATION_JSON_VALUE)
	public Policy getPolicyById(@PathVariable("policyId") Long policyId);

	@Retry(name = "insurance-feign-retry")
	@CircuitBreaker(name = "insurance-feign-cb", fallbackMethod = "fallbackForGetAllPoliciesByNameLike")
	@GetMapping(value = "/policy/name-like/{namePhrase}", produces = MediaType.APPLICATION_JSON_VALUE)
	public List<Policy> getAllPoliciesByNameLike(@PathVariable("namePhrase") String namePhrase);

	@Retry(name = "insurance-feign-retry")
	@CircuitBreaker(name = "insurance-feign-cb", fallbackMethod = "fallbackForGetPolicyByName")
	@GetMapping(value = "/policy/name/{policyName}", produces = MediaType.APPLICATION_JSON_VALUE)
	public Policy getPolicyByName(@PathVariable("policyName") String policyName);

	@Retry(name = "insurance-feign-retry")
	@CircuitBreaker(name = "insurance-feign-cb", fallbackMethod = "fallbackForDeletePolicyById")
	@DeleteMapping(value = "/policy/{policyId}")
	@ResponseStatus(value = HttpStatus.NO_CONTENT)
	public void deletePolicyById(@PathVariable("policyId") Long policyId);

	// ====================ISSUE POLICY
	// METHODS=======================================//

	@Retry(name = "insurance-feign-retry")
	@CircuitBreaker(name = "insurance-feign-cb", fallbackMethod = "fallbackForAddIssuedPolicy")
	@PostMapping(value = "/issue-policy/{policyId}/{customerId}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(value = HttpStatus.CREATED)
	public ResponseEntity<IssuePolicy> addIssuedPolicy(@PathVariable("policyId") Long policyId,
			@PathVariable("customerId") Long customerId);

	@Retry(name = "insurance-feign-retry")
	@CircuitBreaker(name = "insurance-feign-cb", fallbackMethod = "fallbackForUpdatePolicyStatus")
	@PutMapping(value = "/issue-policy/{policyId}/{customerId}/{status}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<IssuePolicy> updatePolicyStatus(@PathVariable("policyId") Long policyId,
			@PathVariable("customerId") Long customerId, @PathVariable("status") int status);

	@Retry(name = "insurance-feign-retry")
	@CircuitBreaker(name = "insurance-feign-cb", fallbackMethod = "fallbackForGetAllIssuedPolicies")
	@GetMapping(value = "/issue-policy", produces = MediaType.APPLICATION_JSON_VALUE)
	public List<IssuePolicy> getAllIssuedPolicies();

	@Retry(name = "insurance-feign-retry")
	@CircuitBreaker(name = "insurance-feign-cb", fallbackMethod = "fallbackForGetIssuedPoliciesByPolicyId")
	@GetMapping(value = "/issue-policy/policyId/{policyId}", produces = MediaType.APPLICATION_JSON_VALUE)
	public List<IssuePolicy> getIssuedPoliciesByPolicyId(@PathVariable("policyId") Long policyId);

	@Retry(name = "insurance-feign-retry")
	@CircuitBreaker(name = "insurance-feign-cb", fallbackMethod = "fallbackForGetIssuedPoliciesByCustomerId")
	@GetMapping(value = "/issue-policy/customerId/{customerId}", produces = MediaType.APPLICATION_JSON_VALUE)
	public List<IssuePolicy> getIssuedPoliciesByCustomerId(@PathVariable("customerId") Long customerId);

	@Retry(name = "insurance-feign-retry")
	@CircuitBreaker(name = "insurance-feign-cb", fallbackMethod = "fallbackForGetIssuedPolicyById")
	@GetMapping(value = "/issue-policy/id/{issuedPolicyId}", produces = MediaType.APPLICATION_JSON_VALUE)
	public IssuePolicy getIssuedPolicyById(@PathVariable("issuedPolicyId") Long issuedPolicyId);

	@Retry(name = "insurance-feign-retry")
	@CircuitBreaker(name = "insurance-feign-cb", fallbackMethod = "fallbackForDeleteIssuedPolicyById")
	@DeleteMapping(value = "/issue-policy/{issuedPolicyId}")
	@ResponseStatus(value = HttpStatus.NO_CONTENT)
	public void deleteIssuedPolicyById(@PathVariable("issuedPolicyId") Long issuedPolicyId);

	@Retry(name = "insurance-feign-retry")
	@CircuitBreaker(name = "insurance-feign-cb", fallbackMethod = "fallbackForDeleteIssuedPolicyByCustomerId")
	@DeleteMapping(value = "/issue-policy/customer-id/{customerId}")
	@ResponseStatus(value = HttpStatus.NO_CONTENT)
	public void deleteIssuedPolicyByCustomerId(@PathVariable("customerId") Long customerId);

	@Retry(name = "insurance-feign-retry")
	@CircuitBreaker(name = "insurance-feign-cb", fallbackMethod = "fallbackForDeleteIssuedPolicyByPolicyId")
	@DeleteMapping(value = "/issue-policy/policy-id/{policyId}")
	@ResponseStatus(value = HttpStatus.NO_CONTENT)
	public void deleteIssuedPolicyByPolicyId(@PathVariable("policyId") Long policyId);

	// =============================FALLBACKS FOR
	// POLICY====================================//
	public default ResponseEntity<Policy> fallbackForAddPolicy(Policy policy, Throwable cause) {
		if (cause instanceof NotFoundException) {
			throw (NotFoundException) cause;
		}
		System.err.println("Exception: => " + cause.getMessage());
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
				.body(new Policy(0L, "Fallback policy", BigDecimal.ZERO, BigDecimal.ZERO, 0, BigDecimal.ZERO));
	}

	public default ResponseEntity<String> fallbackForUpdatePolicy(Long policyId, Policy updatedPolicy,
			Throwable cause) {
		if (cause instanceof NotFoundException) {
			throw (NotFoundException) cause;
		}
		System.err.println("Exception: => " + cause.getMessage());
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Policy update failed. Fallback invoked.");
	}

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

	public default void fallbackForDeletePolicyById(Long policyId, Throwable cause) {
		if (cause instanceof NotFoundException) {
			throw (NotFoundException) cause;
		}
		System.err.println("Exception: => " + cause.getMessage());
		System.err.println("Error: Delete operation failed for policy with ID: " + policyId);
	}

	// =============================FALLBACKS FOR ISSUE
	// POLICY====================================//
	public default ResponseEntity<IssuePolicy> fallbackForAddIssuedPolicy(Long policyId, Long customerId, Throwable cause) {
		if (cause instanceof NotFoundException) {
			throw (NotFoundException) cause;
		}
		System.err.println("Exception: => " + cause.getMessage());
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
				.body(new IssuePolicy(0L, 0L, 0L, LocalDateTime.now(), 0));
	}

	public default ResponseEntity<IssuePolicy> fallbackForUpdatePolicyStatus(Long policyId, Long customerId, int status,
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

	public default void fallbackForDeleteIssuedPolicyByCustomerId(Long customerId, Throwable cause) {
		if (cause instanceof NotFoundException) {
			throw (NotFoundException) cause;
		}
		System.err.println("Exception: => " + cause.getMessage());
		System.err.println("Error: Delete operation failed for issued policy with customer ID: " + customerId);
	}
	
	public default void fallbackForDeleteIssuedPolicyByPolicyId(Long policyId, Throwable cause) {
		if (cause instanceof NotFoundException) {
			throw (NotFoundException) cause;
		}
		System.err.println("Exception: => " + cause.getMessage());
		System.err.println("Error: Delete operation failed for issued policy with policy ID: " + policyId);
	}
	
	
	public default void fallbackForDeleteIssuedPolicyById(Long issuedPolicyId, Throwable cause) {
		if (cause instanceof NotFoundException) {
			throw (NotFoundException) cause;
		}
		System.err.println("Exception: => " + cause.getMessage());
		System.err.println("Error: Delete operation failed for issued policy with ID: " + issuedPolicyId);
	}
}
