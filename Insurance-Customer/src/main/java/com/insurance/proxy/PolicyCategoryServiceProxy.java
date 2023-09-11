package com.insurance.proxy;

import java.util.Collections;
import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.insurance.dto.PolicyCategory;
import com.insurance.exception.NotFoundException;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;

@FeignClient(name = "policy-category-service")
public interface PolicyCategoryServiceProxy {

	@Retry(name = "user-feign-retry")
	@CircuitBreaker(name = "user-feign-cb", fallbackMethod = "fallbackForGetAllCategories")
    @GetMapping(value = "/category", produces = MediaType.APPLICATION_JSON_VALUE)
    List<PolicyCategory> getAllCategories();

	@Retry(name = "user-feign-retry")
	@CircuitBreaker(name = "user-feign-cb", fallbackMethod = "fallbackForGetAllCategoriesByNameLike")
    @GetMapping(value = "/category/name-like/{namePhrase}", produces = MediaType.APPLICATION_JSON_VALUE)
    List<PolicyCategory> getAllCategoriesByNameLike(@PathVariable("namePhrase") String namePhrase);

	@Retry(name = "user-feign-retry")
	@CircuitBreaker(name = "user-feign-cb", fallbackMethod = "fallbackForGetCategoryById")
    @GetMapping(value = "/category/{categoryId}", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<PolicyCategory> getCategoryById(@PathVariable("categoryId") Long categoryId);

	@Retry(name = "user-feign-retry")
	@CircuitBreaker(name = "user-feign-cb", fallbackMethod = "fallbackForGetCategoryByName")
    @GetMapping(value = "/category/name/{categoryName}", produces = MediaType.APPLICATION_JSON_VALUE)
    PolicyCategory getCategoryByName(@PathVariable("categoryName") String categoryName);
	
	@Retry(name = "user-feign-retry")
	@CircuitBreaker(name = "user-feign-cb", fallbackMethod = "fallbackForGetCategoryByPolicyId")
    @GetMapping(value = "/category/policy-id/{policyId}", produces = MediaType.APPLICATION_JSON_VALUE)
    PolicyCategory getCategoryByPolicyId(@PathVariable("policyId") Long policyId);
	//=============================FALLBACKS====================================//
	
    public default List<PolicyCategory> fallbackForGetAllCategories(Throwable cause) {
    	if (cause instanceof NotFoundException) {
            throw (NotFoundException) cause;
        }
    	System.err.println("Exception: => " + cause.getMessage());
        return Collections.emptyList();
    }

 
    public default List<PolicyCategory> fallbackForGetAllCategoriesByNameLike(String namePhrase, Throwable cause) {
    	if (cause instanceof NotFoundException) {
            throw (NotFoundException) cause;
        }
    	System.err.println("Exception: => " + cause.getMessage());
        return Collections.emptyList();
    }

   
    public default ResponseEntity<PolicyCategory> fallbackForGetCategoryById(Long categoryId, Throwable cause) {
    	if (cause instanceof NotFoundException) {
            throw (NotFoundException) cause;
        }
    	System.err.println("Exception: => " + cause.getMessage());
    	 return new ResponseEntity<>(new PolicyCategory(categoryId, "Default Category", Collections.emptyList()),HttpStatus.OK);
    }

   
    public default PolicyCategory fallbackForGetCategoryByPolicyId(Long policyId, Throwable cause) {
    	if (cause instanceof NotFoundException) {
            throw (NotFoundException) cause;
        }
    	System.err.println("Exception: => " + cause.getMessage());
        return new PolicyCategory(0L, "Default Category", Collections.emptyList());
    }

    public default PolicyCategory fallbackForGetCategoryByName(String categoryName, Throwable cause) {
    	if (cause instanceof NotFoundException) {
            throw (NotFoundException) cause;
        }
    	System.err.println("Exception: => " + cause.getMessage());
        return new PolicyCategory(0L, categoryName, Collections.emptyList());
    }
}
