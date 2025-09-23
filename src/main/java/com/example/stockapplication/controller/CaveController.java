package com.example.stockapplication.controller;

import com.example.stockapplication.entity.*;
import com.example.stockapplication.service.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class CaveController {

    private final CaveUserService caveuserService; // final + RequiredArgsConstructor

    @GetMapping("/get_users")
    public ResponseEntity<List<CaveUser>> getAllUsers() {
        return ResponseEntity.ok(caveuserService.getAllUsers());
    }


    private final CaveLogsService cavelogsService; // final + RequiredArgsConstructor

    /**
     * Get all logs
     * @return list of all logs
     */
    @GetMapping("/get_logs")
    public ResponseEntity<List<CaveLogs>> getAllLogs() {
        return ResponseEntity.ok(cavelogsService.getAllLogs());
    }

    /**
     * Get recent logs with custom limit
     * @param limit number of recent logs to retrieve
     * @return list of recent logs
     */
    @GetMapping("/get_recent_logs")
    public ResponseEntity<List<CaveLogs>> getRecentLogs(@RequestParam int limit) {
        List<CaveLogs> recentLogs = cavelogsService.getRecentLogs(limit);
        return ResponseEntity.ok(recentLogs);
    }

    /**
     * Get 50 most recent logs
     * @return list of 50 recent logs
     */
    @GetMapping("/get_recent_logs/50")
    public ResponseEntity<List<CaveLogs>> getRecent50Logs() {
        return ResponseEntity.ok(cavelogsService.getRecent50Logs());
    }

    /**
     * Get 100 most recent logs
     * @return list of 100 recent logs
     */
    @GetMapping("/get_recent_logs/100")
    public ResponseEntity<List<CaveLogs>> getRecent100Logs() {
        return ResponseEntity.ok(cavelogsService.getRecent100Logs());
    }

    /**
     * Add a new log entry using URL parameters
     * @param operation the operation type
     * @param description the log description
     * @return success message
     */
    @PostMapping("/add_log_url")
    public ResponseEntity<String> addLogUrl(@RequestParam String operation, @RequestParam String description) {
        cavelogsService.addLog(operation, description);
        return ResponseEntity.ok("Log added successfully via URL parameters");
    }

    /**
     * Add a new log entry using GET request with URL parameters (alternative approach)
     * @param operation the operation type
     * @param description the log description
     * @return success message
     */
    @GetMapping("/add_log_get")
    public ResponseEntity<String> addLogGet(@RequestParam String operation, @RequestParam String description) {
        cavelogsService.addLog(operation, description);
        return ResponseEntity.ok("Log added successfully via GET");
    }

    /**
     * Alternative endpoint - Add log using entity
     * @param caveLogs the log entity
     * @return the created log
     */
    @PostMapping("/save_log")
    public ResponseEntity<CaveLogs> saveLog(@RequestBody CaveLogs caveLogs) {
        CaveLogs savedLog = cavelogsService.saveLog(caveLogs);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedLog);
    }

    /**
     * DTO for log creation requests
     */
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class LogCreateRequest {
        private String operation;
        private String description;
    }

    private final CaveSupplierService cavesupplierService; // final + RequiredArgsConstructor

    @GetMapping("/get_supplier")
    public ResponseEntity<List<CaveSupplier>> getAllSupplier() {
        return ResponseEntity.ok(cavesupplierService.getAllLogs());
    }

    private final CaveStockService cavestockService; // final + RequiredArgsConstructor

    @GetMapping("/get_stock")
    public ResponseEntity<List<CaveStock>> getAllStock() {
        return ResponseEntity.ok(cavestockService.getAllStock());
    }

    private final CaveHandlerService caveHandlerService; // final + RequiredArgsConstructor

    @GetMapping("/get_handler")
    public ResponseEntity<List<CaveHandler>> getAllHandler() {
        return ResponseEntity.ok(caveHandlerService.getAllHandler());
    }

    @GetMapping("/get_user_by_lastname")
    public ResponseEntity<List<String>> getTotalsByLastName(@RequestParam("name") String name) {
        return ResponseEntity.ok(caveuserService.getTotalsByLastName(name));
    }

}
