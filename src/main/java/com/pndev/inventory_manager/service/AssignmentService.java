package com.pndev.inventory_manager.service;

import com.pndev.inventory_manager.entity.Assignment;
import com.pndev.inventory_manager.entity.Employee;
import com.pndev.inventory_manager.entity.Equipment;
import com.pndev.inventory_manager.repository.AssignmentRepository;
import com.pndev.inventory_manager.repository.EmployeeRepository;
import com.pndev.inventory_manager.repository.EquipmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AssignmentService {

    private final AssignmentRepository assignmentRepository;
    private final EmployeeRepository employeeRepository;
    private final EquipmentRepository equipmentRepository;

    public Assignment assignEquipment(Long employeeId, Long equipmentId, String notes){
        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new IllegalArgumentException("Employee not found"));

        Equipment equipment = equipmentRepository.findById(equipmentId)
                .orElseThrow(() -> new IllegalArgumentException("Equipment not found"));

        if (equipment.getStatus() != Equipment.EquipmentStatus.AVAILABLE) {
            throw new IllegalStateException("Equipment is not available for assignment");
        }

        equipment.setStatus(Equipment.EquipmentStatus.ASSIGNED);
        equipmentRepository.save(equipment);

        Assignment assignment = Assignment.builder()
                .employee(employee)
                .equipment(equipment)
                .startDate(LocalDate.now())
                .notes(notes)
                .build();

        return assignmentRepository.save(assignment);
    }

    public Assignment returnEquipment(Long assignmentId) {
        Assignment assignment = assignmentRepository.findById(assignmentId)
                .orElseThrow(() -> new IllegalArgumentException("Assignment not found"));

        if (assignment.getEndDate() != null) {
            throw new IllegalStateException("Equipment already returned");
        }

        assignment.setEndDate(LocalDate.now());
        assignmentRepository.save(assignment);

        Equipment equipment = assignment.getEquipment();
        equipment.setStatus(Equipment.EquipmentStatus.AVAILABLE);
        equipmentRepository.save(equipment);

        return assignment;
    }

    public List<Assignment> getAssignmentsByEmployee(Long employeeId) {
        return assignmentRepository.findByEmployeeId(employeeId);
    }

    public List<Assignment> getAssignmentsByEquipment(Long equipmentId) {
        return assignmentRepository.findByEquipmentId(equipmentId);
    }
}

