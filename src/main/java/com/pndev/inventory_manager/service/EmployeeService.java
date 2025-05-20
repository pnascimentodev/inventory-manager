package com.pndev.inventory_manager.service;

import com.pndev.inventory_manager.entity.Employee;
import com.pndev.inventory_manager.repository.AssignmentRepository;
import com.pndev.inventory_manager.repository.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final AssignmentRepository assignmentRepository;

    public Employee create(Employee employee) {
        if (employee.getEmail() != null && employeeRepository.findByEmail(employee.getEmail()).isPresent()) {
            throw new IllegalArgumentException("Email already exists");
        }
        return employeeRepository.save(employee);
    }

    public Employee update(Employee employee) {
        if (employee.getId() == null) {
            throw new IllegalArgumentException("Employee ID cannot be null");
        }
        if (employee.getEmail() != null && employeeRepository.findByEmail(employee.getEmail()).isPresent()) {
            throw new IllegalArgumentException("Email already exists");
        }
        return employeeRepository.save(employee);
    }

    public Optional<Employee> findByEmail(String email) {
        return employeeRepository.findByEmail(email);
    }

    public Optional<Employee> findByPhone(String phone) {

        return employeeRepository.findByPhone(phone);
    }

}
