package com.example.stockapplication.repository;

import com.example.stockapplication.entity.CaveStock;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CaveStockRepo extends JpaRepository<CaveStock, Integer>{

    /*
    *
    *  Read
    *
    * */

    List<CaveStock> findByGenre(String genre);

    Optional<CaveStock> findById(Integer stock_id);

    List<CaveStock> findByLabel(String label);

    List<CaveStock> findByYearsBetween(Integer yearsAfter, Integer yearsBefore);

    List<CaveStock> findByPriceBetween(Double price, Double price2);

    /*
     *
     *  Delete
     *
     * */

   CaveStock  deleteCaveStockById(Integer id);

}
