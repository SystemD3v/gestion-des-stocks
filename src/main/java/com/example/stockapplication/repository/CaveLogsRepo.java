package com.example.stockapplication.repository;

import com.example.stockapplication.dto.AddLogRequest;
import com.example.stockapplication.entity.CaveLogs;
import com.example.stockapplication.service.CaveLogsService;
import com.example.stockapplication.entity.CaveStock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface CaveLogsRepo extends JpaRepository<CaveLogs, Long> {
    List<CaveLogs> findAllByOrderByLogIdDesc();  // Changed from Id to LogId
}