package com.insurance.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.insurance.entity.PolicyCategory;
import com.insurance.repository.PolicyCategoryRepository;

@Service(value = "policyCategoryService")
@Scope(value = "singleton")
public class PolicyCategoryService implements IPolicyCategoryService {

	@Autowired
	@Qualifier(value = "policyCategoryRepository")
	private PolicyCategoryRepository policyCategoryRepository;

	@Override
	public PolicyCategory addcategory(PolicyCategory category) {
		return policyCategoryRepository.save(category);
	}

	@Override
	public PolicyCategory updateCategoryName(Long categoryId, String categoryName) {
		PolicyCategory existingCategory = policyCategoryRepository.findById(categoryId).orElse(null);
		if (existingCategory != null) {
			existingCategory.setCategoryName(categoryName);
			return policyCategoryRepository.save(existingCategory);
		} else {
			return null; // Category not found
		}
	}

	@Override
	public PolicyCategory addPolicyId(Long categoryId, Long policyId) {
		PolicyCategory existingCategory = policyCategoryRepository.findById(categoryId).orElse(null);
		existingCategory.getPolicyIds().add(policyId);
		return policyCategoryRepository.save(existingCategory);
	}

	@Override
	public boolean removePolicyId(Long policyId) {
		List<PolicyCategory> categories = policyCategoryRepository.findByPolicyIdsContaining(policyId);
		if (categories != null) {
			for (PolicyCategory category : categories) {
				category.getPolicyIds().remove(policyId);
				policyCategoryRepository.save(category);
			}
			return true;
		}
		return false;
	}

	@Override
	public List<PolicyCategory> getAllCategories() {
		return policyCategoryRepository.findAll();
	}

	@Override
	public List<PolicyCategory> getAllCategoriesByNameLike(String namePhrase) {
		return policyCategoryRepository.findByCategoryNameIgnoreCaseLike("%" + namePhrase + "%");
	}

	@Override
	public PolicyCategory getCategoryById(Long categoryId) {
		return policyCategoryRepository.findById(categoryId).orElse(null);
	}

	@Override
	public PolicyCategory getCategoryByName(String name) {
		return policyCategoryRepository.findByCategoryName(name);
	}

	@Override
	public void deleteCategoryById(Long id) {
		policyCategoryRepository.deleteById(id);

	}

}
