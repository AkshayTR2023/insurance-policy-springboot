package com.insurance.repository;

import org.springframework.context.annotation.Scope;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.insurance.entity.Address;
@Repository(value = "addressRepository")
@Scope(value = "singleton")
public interface AddressRepository extends JpaRepository<Address, Long> {

}
