package com.insurance.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.insurance.entity.Address;
import com.insurance.repository.AddressRepository;

@Service(value = "addressService")
@Scope(value = "singleton")
public class AddressService implements IAddressService {

	@Autowired
	@Qualifier(value = "addressRepository")
	private AddressRepository addressRepository;

	@Override
	public Address addAddress(Long userId, Address address) {
		address.setAddressId(userId);
		return addressRepository.save(address);
	}

	@Override
	public Address updateAddress(Long addressId, Address address) {
		if (addressRepository.findById(addressId).get() != null) {
			address.setAddressId(addressId);
			return addressRepository.save(address);
		}
		return null;
	}

	@Override
	public Address getAddress(Long addressId) {
		return addressRepository.findById(addressId).orElse(null);
	}

	@Override
	public List<Address> getAllAddresses() {
		return addressRepository.findAll();
	}

	@Override
	public void deleteAddress(Long addressId) {
		addressRepository.deleteById(addressId);

	}

}
