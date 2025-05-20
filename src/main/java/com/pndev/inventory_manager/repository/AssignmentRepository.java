package com.pndev.inventory_manager.repository;

import com.pndev.inventory_manager.entity.Assignment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AssignmentRepository extends JpaRepository<Assignment, Long> {

    List<Assignment> findByEmployeeId(Long employeeId);

    List<Assignment> findByEquipmentId(Long equipmentId);
}
