package com.insurance.service;

import java.util.List;

import com.insurance.entity.Address;

public interface IAddressService {

	public Address addAddress(Long userId, Address address);
	
	public Address updateAddress(Long addressId, Address address);
	
	public Address getAddress(Long addressId);
	public List<Address> getAllAddresses();
	
	public void deleteAddress(Long addressId);
}
