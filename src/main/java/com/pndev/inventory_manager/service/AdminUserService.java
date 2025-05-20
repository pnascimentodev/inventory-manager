package com.pndev.inventory_manager.service;

import com.pndev.inventory_manager.entity.AdminUser;
import com.pndev.inventory_manager.repository.AdminUserReposiroty;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AdminUserService {
    private final AdminUserReposiroty adminUserReposiroty;
    private final PasswordEncoder passwordEncoder;


    public AdminUser create(AdminUser adminUser) {
        if (adminUserReposiroty.findByUsername(adminUser.getUsername()).isPresent()) {
            throw new IllegalArgumentException("Username already exists");
        }
        adminUser.setPassword(passwordEncoder.encode(adminUser.getPassword()));
        return adminUserReposiroty.save(adminUser);
    }

    public Optional<AdminUser> findByUsername(String username) {
        return adminUserReposiroty.findByUsername(username);
    }

    public List<AdminUser> findAllByRole(String role) {
        return adminUserReposiroty.findAllByRole(role);
    }
}
