package com.example.stockapplication.controller;

import com.example.stockapplication.entity.*;
import com.example.stockapplication.service.*;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class CaveController {

    private final CaveUserService caveuserService; // final + RequiredArgsConstructor

    private final CaveStockService cavestockService;

    private final CaveLogsService cavelogsService; // final + RequiredArgsConstructor

    private final CaveHandlerService caveHandlerService; // final + RequiredArgsConstructor

    private final CaveSupplierService cavesupplierService; // final + RequiredArgsConstructor


    /***********************************************************
     *
     * CAVE_LOGS
     *
     **********************************************************/

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


    /***********************************************************
     *
     * CAVE_STOCK
     *
     **********************************************************/

    @GetMapping("/get_stock")
    public ResponseEntity<List<CaveStock>> getAllStock(){
        return ResponseEntity.ok(cavestockService.getAllStock());
    }

    @GetMapping("/get_stock/{genre}")
    public ResponseEntity<List<CaveStock>> getStockByGenre(@PathVariable String genre) {
        return ResponseEntity.ok(cavestockService.getStockByGenre(genre));
    }

    @GetMapping("/get_stockById/{id}")
    public ResponseEntity<Optional<CaveStock>> getStockById(@PathVariable Integer id){
        return ResponseEntity.ok(cavestockService.getStockById(id));
    }

    @GetMapping("/get_stockByLabel/{label}")
    public ResponseEntity<List<CaveStock>> getStockByLabel(@PathVariable String label){
        return ResponseEntity.ok(cavestockService.getStockByLabel(label));
    }

    @GetMapping("/get_stockByYear/{firstYear}/{secondYear}")
    public ResponseEntity<List<CaveStock>> getStockByYear(@PathVariable Integer firstYear, @PathVariable Integer secondYear){
        return ResponseEntity.ok(cavestockService.getStockByYears(firstYear, secondYear));
    }

    @GetMapping("/get_stockByPrice/{lowPrice}/{highPrice}")
    public ResponseEntity<List<CaveStock>> getStockByPrice(@PathVariable Double lowPrice, @PathVariable Double highPrice){
        return ResponseEntity.ok(cavestockService.getStockByPrice(lowPrice, highPrice));
    }

    @GetMapping("/delete_stockById/{id}")
    public ResponseEntity<CaveStock> deleteStockById(@PathVariable Integer id){
        return ResponseEntity.ok(cavestockService.deleteStockById(id));
    }

    @GetMapping("/updateStock/{id}/{var}/{value}")
    public void updateStock(@PathVariable Integer id, @PathVariable String var, @PathVariable String value){
        cavestockService.update(id, var, value);
    }

    @PostMapping(path= "/createStock")
    public CaveStock createStock(@RequestBody CaveStock caveStock){
        cavestockService.createStock(caveStock);

        return caveStock;
    }

    /***********************************************************
     *
     * CAVE_HANDLER
     *
     **********************************************************/


    @GetMapping("/get_handler")
    public ResponseEntity<List<CaveHandler>> getAllHandler() {
        return ResponseEntity.ok(caveHandlerService.getAllHandler());
    }


    /***********************************************************
     *
     * CAVE_USER
     *
     **********************************************************/


    @GetMapping("/get_user_by_lastname")
    public ResponseEntity<List<String>> getTotalsByLastName(@RequestParam("name") String name) {
        return ResponseEntity.ok(caveuserService.getTotalsByLastName(name));
    }

    @GetMapping("/get_users")
    public ResponseEntity<List<CaveUser>> getAllUsers() {
        return ResponseEntity.ok(caveuserService.getAllUsers());
    }


    /***********************************************************
     *
     * CAVE_SUPPLIER
     *
     **********************************************************/

    @GetMapping("/get_supplier")
    public ResponseEntity<List<CaveSupplier>> getAllSupplier() {
        return ResponseEntity.ok(cavesupplierService.getAllLogs());
    }

}
