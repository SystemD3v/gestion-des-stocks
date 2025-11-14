package com.example.stockapplication.repository;

import com.example.stockapplication.entity.CaveHandler;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CaveHandlerRepo extends JpaRepository<CaveHandler, Integer>{

    Optional<CaveHandler> findById(Integer id);

    List<CaveHandler> findByOrderTimestampBetween(String firstTimestamp, String secondTimestamp);

    List<CaveHandler> findByUserId(Integer userId);

    List<CaveHandler> findByValidated(boolean validated);

    List<CaveHandler> findByCompleted(boolean completed);

    List<CaveHandler> findByStockId(Integer stockId);

    List<CaveHandler> findTopByOrderByIdDesc();

}
