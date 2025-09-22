package com.example.stockapplication.service;

import com.example.stockapplication.entity.CaveController;
import com.example.stockapplication.entity.CaveStock;
import com.example.stockapplication.repository.CaveStockRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class CaveStockService {

    private final CaveStockRepo cavestockRepo;

    public List<CaveStock> getAllStock() {
        return cavestockRepo.findAll();
    }


}