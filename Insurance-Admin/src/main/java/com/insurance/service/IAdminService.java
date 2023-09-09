package com.insurance.service;

import java.util.List;

import com.insurance.entity.Admin;

public interface IAdminService {

	public Admin registerAdmin(Admin admin);

	public Admin updateAdmin(Long adminId, Admin admin);

	public Admin checkLoginCredentials(String adminEmail, String adminPassword);

	public Admin getAdminById(Long adminId);
	public Admin getAdminByEmail(String adminEmail);
	public List<Admin> getAllAdmins();

}
