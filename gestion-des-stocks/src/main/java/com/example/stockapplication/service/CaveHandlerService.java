package com.example.stockapplication.service;

import com.example.stockapplication.entity.CaveHandler;
import com.example.stockapplication.entity.CaveStock;
import com.example.stockapplication.repository.CaveHandlerRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class CaveHandlerService {

    private final CaveHandlerRepo caveHandlerRepo;

    public List<CaveHandler> getAllHandler() {      // ← renommé
        return caveHandlerRepo.findAll();
    }

    public Optional<CaveHandler> findById(Integer id){

        return caveHandlerRepo.findById(id);
    }

    public List<CaveHandler> findByOrderTimestampBetween(String firstTimestamp, String secondTimestamp){

        return caveHandlerRepo.findByOrderTimestampBetween(firstTimestamp, secondTimestamp);
    }

    public List<CaveHandler> findByUserId(Integer userId){
        return caveHandlerRepo.findByUserId(userId);
    }

    public List<CaveHandler> findByValidated(boolean validated){
        return caveHandlerRepo.findByValidated(validated);
    }

    public List<CaveHandler> findByCompleted(boolean completed){
        return caveHandlerRepo.findByCompleted(completed);
    }

    public List<CaveHandler> findByStockId(Integer stockId){
        return caveHandlerRepo.findByStockId(stockId);
    }

    public void deleteHandlerById(Integer id){
        caveHandlerRepo.deleteById(id);
    }

    public CaveHandler createInstance(CaveHandler caveHandler){
        return caveHandlerRepo.save(caveHandler);
    }

    public void update(Integer id, String var, String value){

        CaveHandler caveHandler = caveHandlerRepo.findById(id).get();

        switch (var) {
            case "id":
                caveHandler.setId(Integer.valueOf(value));
                break;
            case "orderTimestamp":
                caveHandler.setOrderTimestamp(value);
                break;
            case "stockId":
                caveHandler.setStockId(Integer.valueOf(value));
                break;
            case "userId":
                caveHandler.setUserId(Integer.valueOf(value));
                break;
            case "completed":
                caveHandler.setCompleted(Boolean.parseBoolean(value));
                break;
            case "validated":
                caveHandler.setValidated(Boolean.parseBoolean(value));
                break;
            case "supplier":
                caveHandler.setOperation(Integer.valueOf(value));
                break;
            case "requestedAmount":
                caveHandler.setRequestAmount(Integer.valueOf(value));
            case "supplyGroupId":
                caveHandler.setSupplyGroupId(Integer.valueOf(value));
            case "supplierId":
                caveHandler.setSupplierId(Integer.valueOf(value));
            default:
                break;
        };

        caveHandlerRepo.save(caveHandler);
    }

}
