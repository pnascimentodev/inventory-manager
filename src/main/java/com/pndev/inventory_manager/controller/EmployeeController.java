package com.pndev.inventory_manager.controller;

import com.pndev.inventory_manager.dto.employee.EmployeeRequest;
import com.pndev.inventory_manager.dto.employee.EmployeeResponse;
import com.pndev.inventory_manager.entity.Employee;
import com.pndev.inventory_manager.security.JwtUtil;
import com.pndev.inventory_manager.service.AdminUserService;
import com.pndev.inventory_manager.service.EmployeeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/employees")
@RequiredArgsConstructor
public class EmployeeController {

    private final EmployeeService employeeService;
    private final AdminUserService adminUserService;
    private final JwtUtil jwtUtil;


    @PostMapping("/create")
    public ResponseEntity<EmployeeResponse> createEmployee(@Valid @RequestBody EmployeeRequest request) {
        Employee createdEmployee = employeeService.create(request);
        return ResponseEntity.ok(EmployeeResponse.fromEntity(createdEmployee));
    }

    @PutMapping("/update")
    public ResponseEntity<EmployeeResponse> updateEmployee(@Valid @RequestBody EmployeeRequest request) {
        Employee updatedEmployee = employeeService.update(request);
        return ResponseEntity.ok(EmployeeResponse.fromEntity(updatedEmployee));
    }

    @GetMapping()
    public ResponseEntity<?> getAllEmployees() {
        return ResponseEntity.ok(employeeService.findAll());
    }

    @GetMapping("/search")
    public ResponseEntity<List<EmployeeResponse>> searchEmployees(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String email,
            @RequestParam(required = false) String phone,
            @RequestParam(required = false) String department){

        List<Employee> employees = employeeService.searchEmployees(name, email, phone, department);

        List<EmployeeResponse> response = employees.stream()
                .map(emp -> new EmployeeResponse(
                        emp.getId(),
                        emp.getName(),
                        emp.getEmail(),
                        emp.getPhone(),
                        emp.getDepartment()))
                .collect(Collectors.toList());

        return ResponseEntity.ok(response);

    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteEmployee(@PathVariable Long id) {
        employeeService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
