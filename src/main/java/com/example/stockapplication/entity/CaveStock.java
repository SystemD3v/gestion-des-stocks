package com.example.stockapplication.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "cave_stocks")

public class CaveStock {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "stocks_id")
    private Integer stock_id;
    private String label;
    private Integer years;
    private String genre;
    private String area;
    private Integer available_quantity;
    private Double price;
}
