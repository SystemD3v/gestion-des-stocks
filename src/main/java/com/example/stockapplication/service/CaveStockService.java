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

    public Optional<CaveStock> getStockById(Integer id){
        return  cavestockRepo.findById(id);
    }

    public List<CaveStock> getStockByGenre(String genre){
        return cavestockRepo.findByGenre(genre);
    }

    public List<CaveStock> getStockByLabel(String label){
        return cavestockRepo.findByLabel(label);
    }

    public List<CaveStock> getStockByYears(Integer firstYear, Integer secondYear){
        return cavestockRepo.findByYearsBetween(firstYear, secondYear);
    }

    public List<CaveStock> getStockByPrice(Integer lowPrice, Integer highPrice){
        return cavestockRepo.findByPriceBetween(lowPrice, highPrice);
    }
}