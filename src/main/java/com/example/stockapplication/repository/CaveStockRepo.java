package com.example.stockapplication.repository;

import com.example.stockapplication.entity.CaveStock;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CaveStockRepo extends JpaRepository<CaveStock, Integer>{

}
