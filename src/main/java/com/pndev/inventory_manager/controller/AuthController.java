package com.pndev.inventory_manager.controller;

import com.pndev.inventory_manager.entity.AdminUser;
import com.pndev.inventory_manager.repository.AdminUserReposiroty;
import com.pndev.inventory_manager.service.AdminUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AdminUserService adminUserService;


    @PostMapping("/register")
    public ResponseEntity<AdminUser> register(@RequestBody AdminUser adminUser) {
        return ResponseEntity.ok(adminUserService.create(adminUser));
    }

    @PostMapping("/login")
    public ResponseEntity<AdminUser> login(@RequestBody AdminUser adminUser) {
        return adminUserService.findByUsername(adminUser.getUsername())
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.badRequest().build());
    }
}
