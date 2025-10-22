package com.example.stockapplication.service;

import com.example.stockapplication.entity.CaveStock;
import com.example.stockapplication.entity.CaveSupplier;
import com.example.stockapplication.entity.CaveUser;
import com.example.stockapplication.repository.CaveStockRepo;
import com.example.stockapplication.repository.CaveSupplierRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class CaveSupplierService {

    private final CaveSupplierRepo cavesupplierRepo;

    private final CaveStockRepo caveStockRepo;

    public List<CaveSupplier> getAllLogs() {      // ← renommé
        return cavesupplierRepo.findAll();
    }

    public Optional<CaveSupplier> getCaveSupplierByid(int id) {
        return cavesupplierRepo.findById(id);
    }

    public void updatesupplier(int id,String name,int phone,String address){
        CaveSupplier caveSupplier = cavesupplierRepo.findById(id).get();

        caveSupplier.setSupplier_name(name);
        caveSupplier.setSupplier_phone(phone);
        caveSupplier.setSupplier_address(address);

        cavesupplierRepo.save(caveSupplier);
    }

    public void deleteById(Integer id){
        cavesupplierRepo.deleteById(id);
    }

    public CaveSupplier createSupp(CaveSupplier caveSupplier) {
        return cavesupplierRepo.save(caveSupplier);
    }
}