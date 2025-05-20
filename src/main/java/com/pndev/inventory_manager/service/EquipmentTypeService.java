package com.pndev.inventory_manager.service;

import com.pndev.inventory_manager.entity.EquipmentType;
import com.pndev.inventory_manager.repository.AssignmentRepository;
import com.pndev.inventory_manager.repository.EquipmentTypeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EquipmentTypeService {

    private final EquipmentTypeRepository equipmentTypeRepository;
    private final AssignmentRepository assignmentRepository;

    public EquipmentType create(EquipmentType equipmentType) {
        if (equipmentTypeRepository.findByName(equipmentType.getName()).isPresent()) {
            throw new IllegalArgumentException("Type already exists");
        }
        return equipmentTypeRepository.save(equipmentType);
    }

    public EquipmentType update(EquipmentType equipmentType) {
        if (equipmentType.getId() == null) {
            throw new IllegalArgumentException("Type ID cannot be null");
        }
        return equipmentTypeRepository.save(equipmentType);
    }

    public void delete(Long id) {
        if (!equipmentTypeRepository.existsById(id)) {
            throw new IllegalArgumentException("Type not found");
        }
        if (!assignmentRepository.findByEquipmentTypeId(id).isEmpty()) {
            throw new IllegalArgumentException("Type cannot be deleted because it is assigned to equipment");
        }

        equipmentTypeRepository.deleteById(id);
    }
}

