package com.example.stockapplication.service;

import com.example.stockapplication.entity.CaveStock;
import com.example.stockapplication.repository.CaveStockRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class CaveStockService {

    private final CaveStockRepo cavestockRepo;

    public List<CaveStock> getAllStock() {
        return cavestockRepo.findAll();
    }

    public CaveStock getStockById(Integer id){
        Optional<CaveStock> optionalCaveStock = cavestockRepo.findById(id);
        if(optionalCaveStock.isPresent()){
            return optionalCaveStock.get();
        }
        log.info("Employee with id: {} doesn't exist", id);
        return null;
    }

    public CaveStock getStockByGenre(String genre){
        return (CaveStock) cavestockRepo.findByGenre(genre);
    }
}