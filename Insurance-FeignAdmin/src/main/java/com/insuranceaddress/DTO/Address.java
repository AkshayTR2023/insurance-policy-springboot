package com.insuranceaddress.DTO;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class Address {
	   @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
		   private long id;
		   private String state;
		   private String address;

}
