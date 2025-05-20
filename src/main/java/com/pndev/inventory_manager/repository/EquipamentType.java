package com.pndev.inventory_manager.repository;

import com.pndev.inventory_manager.entity.EquipmentType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EquipamentType extends JpaRepository<EquipmentType , Long> {
}
