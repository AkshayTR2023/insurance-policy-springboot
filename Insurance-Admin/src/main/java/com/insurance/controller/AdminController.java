package com.insurance.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.insurance.entity.Admin;
import com.insurance.service.IAdminService;

@RestController(value = "adminController")
@Scope(value = "request")
@RequestMapping("/admin")
@CrossOrigin(origins = "*")
public class AdminController {

	@Autowired
	@Qualifier(value = "adminService")
	private IAdminService adminService;

	// ==============================POST=================================//
	@PostMapping(value = "/register", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Admin> registerAdmin(@RequestBody Admin admin) {
		if (adminService.getAdminByEmail(admin.getAdminEmail()) != null) {
			return ResponseEntity.status(HttpStatus.CONFLICT).build();
		}
		Admin registeredAdmin = adminService.registerAdmin(admin);
		return ResponseEntity.status(HttpStatus.CREATED).body(registeredAdmin);
	}

	@PostMapping(value = "/login", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Admin> adminLoginCheck(@RequestBody Admin admin) {
		Admin checkAdmin = adminService.checkLoginCredentials(admin.getAdminEmail(), admin.getAdminPassword());
		if (checkAdmin != null) {
			return new ResponseEntity<>(checkAdmin, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		}
	}

	// ==============================PUT=================================//
	@PutMapping(value = "/{adminId}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Admin> updateAdmin(@PathVariable("adminId") Long adminId, @RequestBody Admin admin) {
		Admin updatedAdmin = adminService.updateAdmin(adminId, admin);
		if (updatedAdmin != null) {
			return new ResponseEntity<>(updatedAdmin, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	// ==============================GET=================================//
	@GetMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
	public List<Admin> getAllAdmins() {
		return adminService.getAllAdmins();
	}

	@GetMapping(value = "/{adminId}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Admin> getAdminById(@PathVariable("adminId") Long adminId) {
		Admin admin = adminService.getAdminById(adminId);
		if (admin != null)
			return new ResponseEntity<>(admin, HttpStatus.OK);
		return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
	}

	@GetMapping(value = "/email/{adminEmail}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Admin> getAdminByEmail(@PathVariable("adminEmail") String adminEmail) {
		Admin admin = adminService.getAdminByEmail(adminEmail);
		if (admin != null)
			return new ResponseEntity<>(admin, HttpStatus.OK);
		return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
	}
}
