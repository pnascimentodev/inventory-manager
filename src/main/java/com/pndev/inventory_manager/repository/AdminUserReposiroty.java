package com.pndev.inventory_manager.repository;

import com.pndev.inventory_manager.entity.AdminUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository

public interface AdminUserReposiroty extends JpaRepository <AdminUser, Long> {
    Optional<AdminUser> findByUsername(String username);

    List<AdminUser> findAllByRole(String role);
}
