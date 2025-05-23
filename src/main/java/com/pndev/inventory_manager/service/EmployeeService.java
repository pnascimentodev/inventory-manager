package com.pndev.inventory_manager.service;

import com.pndev.inventory_manager.dto.employee.EmployeeRequest;
import com.pndev.inventory_manager.entity.Employee;
import com.pndev.inventory_manager.repository.AssignmentRepository;
import com.pndev.inventory_manager.repository.EmployeeRepository;
import jakarta.persistence.criteria.Predicate;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final AssignmentRepository assignmentRepository;

    public Employee create(EmployeeRequest request) {
        if (request.getEmail() != null && employeeRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new IllegalArgumentException("Email already exists");
        }

        Employee employee = new Employee();
        employee.setName(request.getName());
        employee.setEmail(request.getEmail());
        employee.setPhone(request.getPhone());
        employee.setDepartment(request.getDepartment());

        return employeeRepository.save(employee);
    }

 public Employee update(EmployeeRequest request) {
     if (request.getId() == null) {
         throw new IllegalArgumentException("Employee ID cannot be null");
     }

     Employee employee = employeeRepository.findById(request.getId())
             .orElseThrow(() -> new IllegalArgumentException("Employee not found"));

     if (request.getEmail() != null) {
         Optional<Employee> existing = employeeRepository.findByEmail(request.getEmail())
                 .stream()
                 .filter(e -> !e.getId().equals(employee.getId()))
                 .findFirst();
         if (existing.isPresent()) {
             throw new IllegalArgumentException("Email already exists");
         }
     }

     employee.setName(request.getName());
     employee.setEmail(request.getEmail());
     employee.setPhone(request.getPhone());
     employee.setDepartment(request.getDepartment());

     return employeeRepository.save(employee);
 }


    public List<Employee> searchEmployees(String name, String email, String phone, String department) {
        Specification<Employee> spec = (root, query, cb) -> {
            List<jakarta.persistence.criteria.Predicate> predicates = new ArrayList<>();

            if (name != null && !name.isEmpty()) {
                predicates.add(cb.equal(root.get("name"), name));
            }
            if (email != null && !email.isEmpty()) {
                predicates.add(cb.equal(root.get("email"), email));
            }
            if (phone != null && !phone.isEmpty()) {
                predicates.add(cb.equal(root.get("phone"), phone));
            }
            if (department != null && !department.isEmpty()) {
                predicates.add(cb.equal(root.get("department"), department));
            }
            return cb.and(predicates.toArray(new Predicate[0]));
        };

        return employeeRepository.findAll(spec);
    }

    public List<Employee> findAll() {
        return employeeRepository.findAll();
    }

    public void deleteById(Long id) {
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Employee not found"));
        employeeRepository.delete(employee);
    }

}
