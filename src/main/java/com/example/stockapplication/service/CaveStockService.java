package com.example.stockapplication.service;

import com.example.stockapplication.entity.CaveStock;
import com.example.stockapplication.repository.CaveStockRepo;
import jakarta.transaction.Transactional;
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

    public List<CaveStock> getStockByPrice(Double lowPrice, Double highPrice){
        return cavestockRepo.findByPriceBetween(lowPrice, highPrice);
    }

    public void deleteStockById(Integer id){
        cavestockRepo.deleteById(id);
    }

    public void update(Integer id, String var, String value){

        CaveStock caveStock = cavestockRepo.findById(id).get();

         switch (var) {
            case "label":
                caveStock.setLabel(value);
                break;
            case "genre":
                caveStock.setGenre(value);
                break;
            case "years":
                caveStock.setYears(Integer.valueOf(value));
                break;
            case "area":
                caveStock.setArea(value);
                break;
            case "available_quantity":
                caveStock.setQuantity(Integer.valueOf(value));
                break;
            case "price":
                caveStock.setPrice(Double.valueOf(value));
                break;
            case "supplier":
                caveStock.setSupplier_id(Integer.valueOf(value));
                break;
             default:
                 break;
        };

         cavestockRepo.save(caveStock);
    }

    public CaveStock createStock(CaveStock caveStock){
        return cavestockRepo.save(caveStock);
    }
}