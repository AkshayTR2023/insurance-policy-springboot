package com.insurance.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.insurance.entity.Admin;
import com.insurance.repository.AdminRepository;

@Service(value = "adminService")
@Scope(value = "singleton")
public class AdminService implements IAdminService {

	@Autowired
	@Qualifier(value = "adminRepository")
	private AdminRepository adminRepository;

	@Override
	public Admin registerAdmin(Admin admin) {
		return adminRepository.save(admin);
	}

	@Override
	public Admin updateAdmin(Long adminId, Admin admin) {
		if (adminRepository.findById(adminId).orElse(null) != null) {
			admin.setAdminId(adminId);
			return adminRepository.save(admin);
		}
		return null;
	}

	@Override
	public Admin checkLoginCredentials(String adminEmail, String adminPassword) {
		return adminRepository.findByAdminEmailAndAdminPassword(adminEmail, adminPassword);
	}

	@Override
	public Admin getAdminById(Long adminId) {
		return adminRepository.findById(adminId).orElse(null);
	}

	@Override
	public Admin getAdminByEmail(String adminEmail) {
		return adminRepository.findByAdminEmail(adminEmail);
	}

	@Override
	public List<Admin> getAllAdmins() {
		return adminRepository.findAll();
	}

}
