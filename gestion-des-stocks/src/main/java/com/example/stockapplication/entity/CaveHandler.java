package com.example.stockapplication.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "cave_controller")

public class CaveHandler {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "instance_id")

    private Integer id;
    private Integer supplyGroupId;
    private Integer requestAmount;
    private Integer operation;
    private boolean validated;
    private boolean completed;
    private String orderTimestamp;
    private Integer stockId;
    private Integer supplierId;
    private Integer userId;

}
