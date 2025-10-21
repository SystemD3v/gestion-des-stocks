package com.example.stockapplication.service;

import com.example.stockapplication.entity.CaveSupplier;
import com.example.stockapplication.repository.CaveSupplierRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class CaveSupplierService {

    private final CaveSupplierRepo cavesupplierRepo;

    public List<CaveSupplier> getAllLogs() {      // ← renommé
        return cavesupplierRepo.findAll();
    }

    public List<CaveSupplier> getCaveSupplierByid(int id) {
        return cavesupplierRepo.getCaveSupplierByid(id);
    }
}