package com.example.stockapplication.service;

import com.example.stockapplication.entity.CaveLogs;
import com.example.stockapplication.repository.CaveLogsRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class CaveLogsService {

    private final CaveLogsRepo cavelogsRepo;

    public List<CaveLogs> getAllLogs() {      // ← renommé
        return cavelogsRepo.findAll();
    }
}
