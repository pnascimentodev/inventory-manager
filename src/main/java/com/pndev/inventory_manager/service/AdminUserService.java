package com.pndev.inventory_manager.service;

import com.pndev.inventory_manager.entity.AdminUser;
import com.pndev.inventory_manager.repository.AdminUserReposiroty;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AdminUserService implements UserDetailsService {
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

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return findByUsername(username)
                .map(user -> org.springframework.security.core.userdetails.User
                        .withUsername(user.getUsername())
                        .password(user.getPassword())
                        .roles(user.getRole().name())
                        .build())
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));
    }
}
