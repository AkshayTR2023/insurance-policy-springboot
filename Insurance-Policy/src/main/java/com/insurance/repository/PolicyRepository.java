package com.insurance.repository;

import java.util.List;

import org.springframework.context.annotation.Scope;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.insurance.entity.Policy;



@Repository(value = "policyRepository")
@Scope(value = "singleton")
public interface PolicyRepository extends JpaRepository<Policy, Long>{

	List<Policy> findByPolicyNameIgnoreCaseLike(String string);

	Policy findByPolicyName(String name);

}
