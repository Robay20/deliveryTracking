package com.example.packettracerbase.service;

import com.example.packettracerbase.model.Admin;
import com.example.packettracerbase.repository.AdminRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AdminServiceImpl implements AdminService {

    private final AdminRepository adminRepository;

    @Autowired
    public AdminServiceImpl(AdminRepository adminRepository) {
        this.adminRepository = adminRepository;
    }

    @Override
    public List<Admin> getAllAdmins() {
        List<Admin> admins = adminRepository.findAll();
        if (admins.isEmpty()) {
            throw new EntityNotFoundException("No admins found in the database");
        }
        return admins;
    }


    @Override
    public Optional<Admin> getAdminById(String id) {
        Optional<Admin> adminOptional = adminRepository.findById(id);
        if (adminOptional.isEmpty()) {
            throw new EntityNotFoundException("Admin not found with id: " + id);
        }
        return adminOptional;
    }


    @Override
    public Admin createAdmin(Admin admin) {
        // Add validation logic if needed
        return adminRepository.save(admin);
    }

    @Override
    public Admin updateAdmin(String id, Admin adminDetails) {
        Admin admin = adminRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Admin not found with id: " + id));

        admin.setUsername(adminDetails.getUsername());
        admin.setPassword(adminDetails.getPassword());
        admin.setActive(adminDetails.isActive());
        admin.setFirstName(adminDetails.getFirstName());
        admin.setLastName(adminDetails.getLastName());
        admin.setEmail(adminDetails.getEmail());
        admin.setDateOfBirth(adminDetails.getDateOfBirth());

        return adminRepository.save(admin);
    }

    @Override
    public void deleteAdmin(String id) {
        if (!adminRepository.existsById(id)) {
            throw new EntityNotFoundException("Admin not found with id: " + id);
        }
        adminRepository.deleteById(id);
    }
}
