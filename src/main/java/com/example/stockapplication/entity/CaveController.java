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

public class CaveController {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "instance_id")

    private Integer id;
    private Integer supply_group_id;
    private Integer request_amount;
    private Integer operation;
    private boolean validated;
    private boolean completed;
    private String order_timestamp;
    private Integer stock_id;
    private Integer supplier_id;
    private Integer user_id;

}
