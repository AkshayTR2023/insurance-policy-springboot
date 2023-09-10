package com.insurance.service;

import java.util.List;

import com.insurance.entity.PolicyCategory;

public interface IPolicyCategoryService {

	public PolicyCategory addcategory(PolicyCategory category);
	
	public PolicyCategory addPolicyId(Long categoryId, Long policyId);
	public boolean removePolicyId(Long policyId);
	
	public PolicyCategory updateCategoryName(Long categoryId, String categoryName);
	
	public List<PolicyCategory> getAllCategories();
	public List<PolicyCategory> getAllCategoriesByNameLike(String namePhrase);
	public PolicyCategory getCategoryById(Long categoryId);
	public PolicyCategory getCategoryByName(String name);
	public PolicyCategory getCategoryByPolicyId(Long policyId);
	public void deleteCategoryById(Long id);
}
