package com.example.stockapplication.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "cave_supplier")

public class CaveSupplier {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "supplier_id")

    private Integer supplier_id;
    private Integer stock_id;
    private Integer available_quantity;
    private String supplier_name;
    private Double supplier_price;
    private String supplier_address;
}
