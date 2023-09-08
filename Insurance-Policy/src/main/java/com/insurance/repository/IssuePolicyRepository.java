package com.insurance.repository;

import java.util.List;

import org.springframework.context.annotation.Scope;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.insurance.entity.IssuePolicy;


@Repository(value = "issuePolicyRepository")
@Scope(value = "singleton")
public interface IssuePolicyRepository extends JpaRepository<IssuePolicy, Long> {

	IssuePolicy findByPolicyIdAndUserId(Long policyId, Long userId);

	List<IssuePolicy> findByPolicyId(Long policyId);
	List<IssuePolicy> findByUserId(Long userId);

}
