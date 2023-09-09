package com.insurance.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.insurance.entity.Address;
import com.insurance.service.IAddressService;

@RestController(value = "addressController")
@Scope(value = "request")
@RequestMapping("/address")
@CrossOrigin(origins = "*")
public class AddressController {

	@Autowired
	@Qualifier(value = "addressService")
	private IAddressService addressService;

	// ==============================POST=================================//
	@PostMapping(value = "/{userId}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(value = HttpStatus.CREATED)
	public ResponseEntity<Address> addAddress(@PathVariable("userId") Long userId, @RequestBody Address address) {
		return ResponseEntity.status(HttpStatus.CREATED).body(addressService.addAddress(userId, address));
	}

	// ==============================PUT=================================//
	@PutMapping(value = "/{addressId}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Address> updateAddress(@PathVariable("addressId") Long addressId,
			@RequestBody Address address) {
		Address updatedAddress = addressService.updateAddress(addressId, address);
		if (updatedAddress != null) {
			return new ResponseEntity<>(updatedAddress, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	// ==============================GET=================================//
	@GetMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
	public List<Address> getAllAddresses() {
		return addressService.getAllAddresses();
	}

	@GetMapping(value = "/{addressId}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Address> getAddressById(@PathVariable("addressId") Long addressId) {
		Address address = addressService.getAddress(addressId);
		if (address != null)
			return new ResponseEntity<>(address, HttpStatus.OK);
		return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
	}

	// ==============================DEL=================================//

	@DeleteMapping(value = "/{addressId}")
	@ResponseStatus(value = HttpStatus.NO_CONTENT)
	public ResponseEntity<Void> deleteAddressById(@PathVariable("addressId") Long addressId) {
		if (addressService.getAddress(addressId) != null) {
			addressService.deleteAddress(addressId);
			return ResponseEntity.noContent().build();
		} else {
			return ResponseEntity.notFound().build();
		}
	}
}
