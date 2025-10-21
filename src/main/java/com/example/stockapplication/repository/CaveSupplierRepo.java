package com.example.stockapplication.repository;

import com.example.stockapplication.entity.CaveLogs;
import com.example.stockapplication.entity.CaveSupplier;
import com.example.stockapplication.entity.CaveUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CaveSupplierRepo extends JpaRepository<CaveSupplier, Integer>{

    List<CaveSupplier> getCaveSupplierByName(String name);
}
