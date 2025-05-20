package com.pndev.inventory_manager.repository;

import com.pndev.inventory_manager.entity.EquipmentType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EquipmentTypeRepository extends JpaRepository<EquipmentType , Long> {
    Optional<Object> findByName(String name);

    Optional<EquipmentType> findById(Long id);
}
