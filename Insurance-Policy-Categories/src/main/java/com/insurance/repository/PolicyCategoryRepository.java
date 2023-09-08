package com.insurance.repository;

import java.util.List;

import org.springframework.context.annotation.Scope;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.insurance.entity.PolicyCategory;

@Repository(value = "policyCategoryRepository")
@Scope(value = "singleton")
public interface PolicyCategoryRepository extends JpaRepository<PolicyCategory, Long>{

	PolicyCategory findByCategoryName(String categoryName);

	List<PolicyCategory> findByCategoryNameIgnoreCaseLike(String string);

	List<PolicyCategory> findByPolicyIdsContaining(Long policyId);
	
}
