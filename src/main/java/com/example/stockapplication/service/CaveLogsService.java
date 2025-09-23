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

    /**
     * Get all logs
     * @return list of all logs
     */
    public List<CaveLogs> getAllLogs() {
        return cavelogsRepo.findAll();



    }

    /**
     * Get most recent X logs
     * @param limit number of logs to retrieve
     * @return list of recent logs ordered by log_id descending
     */
    public List<CaveLogs> getRecentLogs(int limit) {
        log.info("Retrieving {} recent logs", limit);
        return cavelogsRepo.findRecentLogs(limit);
    }

    /**
     * Convenience method to get 50 recent logs
     * @return list of 50 most recent logs
     */
    public List<CaveLogs> getRecent50Logs() {
        return getRecentLogs(50);
    }

    /**
     * Convenience method to get 100 recent logs
     * @return list of 100 most recent logs
     */
    public List<CaveLogs> getRecent100Logs() {
        return getRecentLogs(100);
    }

    /**
     * Insert a new log entry
     * @param operation the operation type
     * @param description the log description
     */
    public void addLog(String operation, String description) {
        log.info("Adding new log: operation={}, description={}", operation, description);
        cavelogsRepo.insertLog(operation, description);
    }

    /**
     * Alternative method to insert using entity
     * @param caveLogs log entity to save
     * @return saved log entity
     */
    public CaveLogs saveLog(CaveLogs caveLogs) {
        log.info("Saving log entity: {}", caveLogs);
        return cavelogsRepo.save(caveLogs);
    }
}