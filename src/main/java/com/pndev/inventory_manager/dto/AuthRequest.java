package com.pndev.inventory_manager.dto;

import com.pndev.inventory_manager.entity.AdminUser;

public class AuthRequest {

    private String username;
    private String password;
    private AdminUser.Role role;

    public AuthRequest() {

    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public AdminUser.Role getRole() {
        return role;
    }
}
