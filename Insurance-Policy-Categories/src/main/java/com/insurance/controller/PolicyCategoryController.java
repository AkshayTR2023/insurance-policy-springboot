package com.insurance.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.insurance.entity.PolicyCategory;
import com.insurance.service.IPolicyCategoryService;

@RestController(value = "policyCategoryController")
@Scope(value = "request")
@RequestMapping("/category")
public class PolicyCategoryController {

	@Autowired
	@Qualifier(value = "policyCategoryService")
	private IPolicyCategoryService categoryService;

	// ==============================POST=================================//
	@PostMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(value = HttpStatus.CREATED)
	public PolicyCategory addCategory(@RequestBody PolicyCategory category) {
		return categoryService.addcategory(category);
	}

	// ==============================PUT=================================//
	@PutMapping(value = "/update-name/{categoryId}/{name}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<PolicyCategory> updateCategoryName(@PathVariable("categoryId")Long categoryId, @PathVariable("name") String categoryName) {
		PolicyCategory updatedCategory = categoryService.updateCategoryName(categoryId, categoryName);
        if (updatedCategory != null) {
            return new ResponseEntity<>(updatedCategory, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
	}
	
	@PutMapping(value = "/{categoryId}/add-policy-id/{policyId}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<PolicyCategory> addPolicyIdToCategory(@PathVariable("categoryId")Long categoryId, @PathVariable("policyId")Long policyId) {
        PolicyCategory updatedCategory = categoryService.addPolicyId(categoryId, policyId);
        if (updatedCategory != null) {
            return new ResponseEntity<>(updatedCategory, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
	
	@DeleteMapping("/remove-policy/{policyId}")
	public ResponseEntity<String> removePolicyIdFromCategories(@PathVariable Long policyId) {
	    boolean removed = categoryService.removePolicyId(policyId);
	    if (removed) {
	        return ResponseEntity.ok("Policy ID " + policyId + " removed from categories.");
	    } else {
	        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Policy ID " + policyId + " not found in any categories.");
	    }
	}


	// ==============================GET=================================//
	@GetMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
	public List<PolicyCategory> getAllCategories() {
		return categoryService.getAllCategories();
	}

	@GetMapping(value = "/name-like/{namePhrase}", produces = MediaType.APPLICATION_JSON_VALUE)
	public List<PolicyCategory> getAllCategoriesByNameLike(@PathVariable("namePhrase") String namePhrase) {
		return categoryService.getAllCategoriesByNameLike(namePhrase);
	}

	@GetMapping(value = "/{categoryId}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<PolicyCategory> getCategoryById(@PathVariable("categoryId") Long categoryId) {
		 PolicyCategory category = categoryService.getCategoryById(categoryId);
	        if (category != null) {
	            return new ResponseEntity<>(category, HttpStatus.OK);
	        } else {
	            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	        }
	}

	@GetMapping(value = "/name/{categoryName}", produces = MediaType.APPLICATION_JSON_VALUE)
	public PolicyCategory getCategoryByName(@PathVariable("categoryName") String categoryName) {
		return categoryService.getCategoryByName(categoryName);
	}
	// ==============================DEL=================================//

	@DeleteMapping(value = "/{categoryId}")
	@ResponseStatus(value = HttpStatus.NO_CONTENT)
	public void deleteCategoryById(@PathVariable("categoryId") Long categoryId) {
		categoryService.deleteCategoryById(categoryId);
	}
}
