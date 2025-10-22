package com.example.stockapplication.repository;

import com.example.stockapplication.entity.CaveLogs;
import com.example.stockapplication.entity.CaveSupplier;
import com.example.stockapplication.entity.CaveUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface CaveSupplierRepo extends JpaRepository<CaveSupplier, Integer> {

    @Override
    Optional<CaveSupplier> findById(Integer integer);


    //    @Modifying
//    @Transactional
//    @Query("UPDATE CaveSupplier c SET c.supplier_name= :name, c.supplier_phone = :phoneNumber, c.supplier_address = :address WHERE c.id = :id")
//    int updateSupplierById(int id, String name, String phoneNumber, String address);
//
//    @Modifying
//    @Transactional
//    @Query("DELETE FROM CaveSupplier c WHERE c.id = :id")
//    int deleteSupplierById(@Param("id")int id);
}
