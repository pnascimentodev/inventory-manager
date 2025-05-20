    package com.pndev.inventory_manager.service;

    import com.pndev.inventory_manager.entity.Equipment;
    import com.pndev.inventory_manager.entity.EquipmentType;
    import com.pndev.inventory_manager.repository.EquipmentRepository;
    import com.pndev.inventory_manager.repository.EquipmentTypeRepository;
    import lombok.RequiredArgsConstructor;
    import org.springframework.stereotype.Service;

    @Service
    @RequiredArgsConstructor
    public class EquipmentService {

        private final EquipmentRepository equipmentRepository;
        private final EquipmentTypeRepository equipmentTypeRepository;



        public Equipment getEquipmentBySerialNumber(String serialNumber) {
            return equipmentRepository.findBySerialNumber(serialNumber)
                    .orElseThrow(() -> new RuntimeException("Equipment not found"));
        }

        public Equipment getEquipmentByAssetTag(String assetTag) {
            return equipmentRepository.findByAssetTag(assetTag)
                    .orElseThrow(() -> new RuntimeException("Equipment not found"));
        }

        public Equipment getEquipmentByMacAddress(String macAddress) {
            return equipmentRepository.findByMacAddress(macAddress)
                    .orElseThrow(() -> new RuntimeException("Equipment not found"));
        }

        public Equipment addEquipment(Equipment equipment, Long equipmentTypeId) {
            if (equipmentRepository.findBySerialNumber(equipment.getSerialNumber()).isPresent()) {
                throw new RuntimeException("Equipment with this serial number already exists");
            }
            if (equipmentRepository.findByAssetTag(equipment.getAssetTag()).isPresent()) {
                throw new RuntimeException("Equipment with this asset tag already exists");
            }
            if (equipmentRepository.findByMacAddress(equipment.getMacAddress()).isPresent()) {
                throw new RuntimeException("Equipment with this MAC address already exists");
            }
            EquipmentType equipmentType = equipmentTypeRepository.findById(equipmentTypeId)
                    .orElseThrow(() -> new RuntimeException("Equipment type not found"));

            equipment.setType(equipmentType);
            equipment.setStatus(Equipment.EquipmentStatus.AVAILABLE);

            return equipmentRepository.save(equipment);
        }

    }
