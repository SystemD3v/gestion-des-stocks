package com.example.stockapplication.service;

import com.example.stockapplication.dto.AddLogRequest;
import com.example.stockapplication.entity.CaveLogs;
import com.example.stockapplication.entity.CaveStock;
import com.example.stockapplication.repository.CaveLogsRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class CaveLogsService {

    private final CaveLogsRepo caveLogsRepo;

    public List<CaveLogs> getAllLogs() {
        return caveLogsRepo.findAll();
    }

    public List<CaveLogs> getRecentLogs(int limit) {
        Pageable pageable = PageRequest.of(0, limit, Sort.by(Sort.Direction.DESC, "logId"));  // Changed from "id" to "logId"
        return caveLogsRepo.findAll(pageable).getContent();
    }

    public CaveLogs addLog(String operation, String description) {
        CaveLogs newLog = new CaveLogs();
        newLog.setOperation(operation);
        newLog.setDescription(description);
        return caveLogsRepo.save(newLog);
    }
}