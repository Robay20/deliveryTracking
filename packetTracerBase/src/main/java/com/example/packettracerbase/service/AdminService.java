package com.example.packettracerbase.service;

import com.example.packettracerbase.model.Admin;

import java.util.List;
import java.util.Optional;


public interface AdminService {
    List<Admin> getAllAdmins();
    Optional<Admin> getAdminById(String id);
    Admin createAdmin(Admin admin);
    Admin updateAdmin(String id, Admin adminDetails);
    void deleteAdmin(String id);
}
