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

import com.insurance.dto.PolicyCategory;
import com.insurance.exception.NotFoundException;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;

@FeignClient(name = "policy-category-service")
public interface PolicyCategoryServiceProxy {

	@Retry(name = "admin-feign-retry")
	@CircuitBreaker(name = "admin-feign-cb", fallbackMethod = "fallbackForAddCategory")
    @PostMapping(value = "/category", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    PolicyCategory addCategory(@RequestBody PolicyCategory category);

	@Retry(name = "admin-feign-retry")
	@CircuitBreaker(name = "admin-feign-cb", fallbackMethod = "fallbackForUpdateCategory")
	@PutMapping(value = "/category/update-name/{categoryId}/{name}", produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<PolicyCategory> updateCategoryName(@PathVariable("categoryId")Long categoryId, @PathVariable("name") String categoryName);

	
	@Retry(name = "admin-feign-retry")
	@CircuitBreaker(name = "admin-feign-cb", fallbackMethod = "fallbackForAddPolicy")
	@PutMapping(value = "/category/{categoryId}/add-policy-id/{policyId}", produces = MediaType.APPLICATION_JSON_VALUE)
	ResponseEntity<PolicyCategory> addPolicyIdToCategory(@PathVariable("categoryId")Long categoryId, @PathVariable("policyId")Long policyId);

	
	
	@Retry(name = "admin-feign-retry")
	@CircuitBreaker(name = "admin-feign-cb", fallbackMethod = "fallbackForRemovePolicy")
	@DeleteMapping("/category/remove-policy/{policyId}")
	ResponseEntity<String> removePolicyIdFromCategories(@PathVariable Long policyId);

	
	@Retry(name = "admin-feign-retry")
	@CircuitBreaker(name = "admin-feign-cb", fallbackMethod = "fallbackForGetAllCategories")
    @GetMapping(value = "/category", produces = MediaType.APPLICATION_JSON_VALUE)
    List<PolicyCategory> getAllCategories();

	@Retry(name = "admin-feign-retry")
	@CircuitBreaker(name = "admin-feign-cb", fallbackMethod = "fallbackForGetAllCategoriesByNameLike")
    @GetMapping(value = "/category/name-like/{namePhrase}", produces = MediaType.APPLICATION_JSON_VALUE)
    List<PolicyCategory> getAllCategoriesByNameLike(@PathVariable("namePhrase") String namePhrase);

	@Retry(name = "admin-feign-retry")
	@CircuitBreaker(name = "admin-feign-cb", fallbackMethod = "fallbackForGetCategoryById")
    @GetMapping(value = "/category/{categoryId}", produces = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<PolicyCategory> getCategoryById(@PathVariable("categoryId") Long categoryId);

	@Retry(name = "admin-feign-retry")
	@CircuitBreaker(name = "admin-feign-cb", fallbackMethod = "fallbackForGetCategoryByName")
    @GetMapping(value = "/category/name/{categoryName}", produces = MediaType.APPLICATION_JSON_VALUE)
    PolicyCategory getCategoryByName(@PathVariable("categoryName") String categoryName);
	
	@Retry(name = "admin-feign-retry")
	@CircuitBreaker(name = "admin-feign-cb", fallbackMethod = "fallbackForGetCategoryByPolicyId")
    @GetMapping(value = "/category/policy-id/{policyId}", produces = MediaType.APPLICATION_JSON_VALUE)
    PolicyCategory getCategoryByPolicyId(@PathVariable("policyId") Long policyId);

	@Retry(name = "admin-feign-retry")
	@CircuitBreaker(name = "admin-feign-cb", fallbackMethod = "fallbackForDeleteCategoryById")
    @DeleteMapping(value = "/category/{categoryId}")
    void deleteCategoryById(@PathVariable("categoryId") Long categoryId);
	
	//=============================FALLBACKS====================================//
	
    public default PolicyCategory fallbackForAddCategory(PolicyCategory category, Throwable cause) {
    	if (cause instanceof NotFoundException) {
            throw (NotFoundException) cause;
        }
    	System.err.println("Exception: => " + cause.toString());
        return new PolicyCategory(0L, "Default Category", Collections.emptyList());
    }

    public default ResponseEntity<PolicyCategory> fallbackForUpdateCategory(Long categoryId, String categoryName, Throwable cause) {
    	if (cause instanceof NotFoundException) {
            throw (NotFoundException) cause;
        }
    	System.err.println("Exception: => " + cause.getMessage());
        return new ResponseEntity<>(new PolicyCategory(0L, "Default Category", Collections.emptyList()),HttpStatus.OK);
    }
   
    public default ResponseEntity<PolicyCategory> fallbackForAddPolicy(Long categoryId, Long policyId, Throwable cause) {
    	if (cause instanceof NotFoundException) {
            throw (NotFoundException) cause;
        }
    	System.err.println("categoryId: "+categoryId+ " poilcyId: "+policyId);
    	System.err.println("Exception: => " + cause.getMessage());
        return new ResponseEntity<>(new PolicyCategory(0L, "Default Category", Collections.emptyList()),HttpStatus.OK);
    }
    public default  ResponseEntity<String> fallbackForRemovePolicy(Long policyId, Throwable cause) {
    	if (cause instanceof NotFoundException) {
            throw (NotFoundException) cause;
        }
    	System.err.println("poilcyId: "+policyId);
    	System.err.println("Exception: => " + cause.getMessage());
        return new ResponseEntity<>("Fallback",HttpStatus.OK);
    }
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

    public default void fallbackForDeleteCategoryById(Long categoryId, Throwable cause) {
    	if (cause instanceof NotFoundException) {
            throw (NotFoundException) cause;
        }
    	System.err.println("Exception: => " + cause.getMessage());
        System.err.println("Error: Delete operation failed for category with ID: " + categoryId);
    }
}
