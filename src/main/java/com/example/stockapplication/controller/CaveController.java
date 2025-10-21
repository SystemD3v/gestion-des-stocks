package com.example.stockapplication.controller;

import com.example.stockapplication.dto.AddLogRequest;
import com.example.stockapplication.entity.*;
import com.example.stockapplication.repository.CaveUserRepo;
import com.example.stockapplication.service.*;
import jakarta.persistence.criteria.CriteriaBuilder;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    @GetMapping("/get_recent_logs/{limit}")
    public ResponseEntity<List<CaveLogs>> getLogsById(@PathVariable int limit) {
        List<CaveLogs> recentLogs = cavelogsService.getRecentLogs(limit);
        return ResponseEntity.ok(recentLogs);
    }

    /**
     * Add a new log entry using URL parameters
     * @return success message
     */
    @GetMapping("/add_log/{operation}/{description}")
    public ResponseEntity<CaveLogs> addLog(@PathVariable String operation, @PathVariable String description) {
        CaveLogs savedLog = cavelogsService.addLog(operation, description);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedLog);
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
    public ResponseEntity<List<CaveStock>> getStockByPrice(@PathVariable Integer lowPrice, @PathVariable Integer highPrice){
        return ResponseEntity.ok(cavestockService.getStockByPrice(lowPrice, highPrice));
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


    @GetMapping("/get_user_by_lastname/{lastname}")
    public ResponseEntity<List<CaveUser>> getUserByLastname(@PathVariable String lastname) {
        return ResponseEntity.ok(caveuserService.getUserByLastname(lastname));
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
