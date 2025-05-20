package com.pndev.inventory_manager.repository;

import com.pndev.inventory_manager.entity.Equipment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EquipmentRepository extends JpaRepository<Equipment, Long> {
    Optional<Equipment> findBySerialNumber(String serialNumber);

    Optional<Equipment> findByAssetTag(String assetTag);

    Optional<Equipment> findByMacAddress(String macAddress);
}
