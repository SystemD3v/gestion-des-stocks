package com.example.stockapplication.repository;

import com.example.stockapplication.entity.CaveStock;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CaveStockRepo extends JpaRepository<CaveStock, Integer>{

    List<CaveStock> findByGenre(String genre);

    Optional<CaveStock> findById(Integer stock_id);
}
