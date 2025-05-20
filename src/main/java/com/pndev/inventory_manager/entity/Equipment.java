package com.pndev.inventory_manager.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "equipments")
public class Equipment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String brand;

    private String model;

    @Column(unique = true)
    private String serialNumber;

    @Column(unique = true)
    private String assetTag;

    @Column(unique = true)
    private String macAddress;

    @Enumerated(EnumType.STRING)
    private EquipmentStatus status;

    @ManyToOne
    private EquipmentType type;

    public enum EquipmentStatus {
        AVAILABLE,
        ASSIGNED,
        MAINTENANCE,
        RETIRED
    }
}
