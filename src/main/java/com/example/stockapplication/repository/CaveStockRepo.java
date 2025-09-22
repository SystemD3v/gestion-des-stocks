package com.example.stockapplication.repository;

import com.example.stockapplication.entity.CaveLogs;
import com.example.stockapplication.entity.CaveStock;
import com.example.stockapplication.entity.CaveSupplier;
import com.example.stockapplication.entity.CaveUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CaveStockRepo extends JpaRepository<CaveStock, Integer>{


}
