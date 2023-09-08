package com.insurance.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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

import com.insurance.dto.PolicyCategory;
import com.insurance.proxy.PolicyCategoryServiceProxy;


@RestController(value = "policyCategoryController")
@Scope(value = "request")
@RequestMapping("/category")
public class PolicyCategoryConsumerRestController {

	@Autowired
	private PolicyCategoryServiceProxy feignProxy; 
	private Logger log = LoggerFactory.getLogger(PolicyCategoryConsumerRestController.class);
;

	// ==============================POST=================================//
	@PostMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(value = HttpStatus.CREATED)
	public PolicyCategory addCategory(@RequestBody PolicyCategory category) {
		log.debug("inside addCategory");
		return feignProxy.addCategory(category);
	}

	// ==============================PUT=================================//
	@PutMapping(value = "/update-name/{categoryId}/{name}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<PolicyCategory> updateCategoryName(@PathVariable("categoryId")Long categoryId, @PathVariable("name") String categoryName) {
		log.debug("inside updateCategory");
		PolicyCategory updatedCategory = feignProxy.updateCategoryName(categoryId, categoryName).getBody();
        if (updatedCategory != null) {
            return new ResponseEntity<>(updatedCategory, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
	}

	// ==============================GET=================================//
	@GetMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
	public List<PolicyCategory> getAllCategories() {
		log.debug("inside getAllCategoriesCategory");
		return feignProxy.getAllCategories();
	}

	@GetMapping(value = "/name-like/{namePhrase}", produces = MediaType.APPLICATION_JSON_VALUE)
	public List<PolicyCategory> getAllCategoriesByNameLike(@PathVariable("namePhrase") String namePhrase) {
		log.debug("inside getAllCategoriesByNameLike");
		return feignProxy.getAllCategoriesByNameLike(namePhrase);
	}

	@GetMapping(value = "/{categoryId}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<PolicyCategory> getCategoryById(@PathVariable("categoryId") Long categoryId) {
		log.debug("inside getCategoryById");
		 PolicyCategory category = feignProxy.getCategoryById(categoryId).getBody();
	        if (category != null) {
	            return new ResponseEntity<>(category, HttpStatus.OK);
	        } else {
	            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	        }
	}

	@GetMapping(value = "/name/{categoryName}", produces = MediaType.APPLICATION_JSON_VALUE)
	public PolicyCategory getCategoryByName(@PathVariable("categoryName") String categoryName) {
		log.debug("inside getCategoryByName");
		return feignProxy.getCategoryByName(categoryName);
	}
	// ==============================DEL=================================//

	@DeleteMapping(value = "/{categoryId}")
	@ResponseStatus(value = HttpStatus.NO_CONTENT)
	public void deleteCategoryById(@PathVariable("categoryId") Long categoryId) {
		log.debug("inside deleteCategoryById");
		feignProxy.deleteCategoryById(categoryId);
	}
}
