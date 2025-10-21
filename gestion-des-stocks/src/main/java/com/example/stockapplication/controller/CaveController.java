package com.example.stockapplication.controller;

import com.example.stockapplication.entity.*;
import com.example.stockapplication.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.stockapplication.dto.UserSummary;

import java.net.URI;
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
    public ResponseEntity<List<CaveStock>> getStockByPrice(@PathVariable Double lowPrice, @PathVariable Double highPrice){
        return ResponseEntity.ok(cavestockService.getStockByPrice(lowPrice, highPrice));
    }

    @DeleteMapping("/deleteStockById/{id}")
    public String deleteStock(@PathVariable Integer id){
        cavestockService.deleteStockById(id);

        return "Deleted";
    }

    @GetMapping("/updateStock/{id}/{var}/{value}")
    public void updateStock(@PathVariable Integer id, @PathVariable String var, @PathVariable String value){
        cavestockService.update(id,var,value);
    }

    @PostMapping("/createStock")
    public ResponseEntity<String> createStock(@RequestBody CaveStock caveStock) {
        CaveStock newStock = cavestockService.createStock(caveStock);
        return ResponseEntity
                .created(URI.create("/api/v1/stock/" + newStock.getId()))
                .body("Stock created successfully");
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
    public ResponseEntity<List<UserSummary>> getUserByLastname(@PathVariable String lastname) {
        return ResponseEntity.ok(caveuserService.getUserByLastname(lastname));
    }

    @GetMapping("/get_users")
    public ResponseEntity<List<CaveUser>> getAllUsers() {
        return ResponseEntity.ok(caveuserService.getAllUsers());
    }

    @DeleteMapping("/delete_users/{id}")
    String deleteUser(@PathVariable Integer id) {
        caveuserService.deleteById(id);
        return "User bien delete : " + id ;
    }

    @PostMapping("/create_user")
    public ResponseEntity<String> createUser(@RequestBody CaveUser caveUser) {
        CaveUser createdUser = caveuserService.createCaveUser(caveUser);
        return ResponseEntity
                .created(URI.create("/api/v1/users/" + createdUser.getId()))
                .body("User created successfully");
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

    @GetMapping("/get_supplier/{id}")
    public ResponseEntity<List<CaveSupplier>> getCaveSupplierByid(@PathVariable int id) {
        return ResponseEntity.ok(cavesupplierService.getCaveSupplierByid(id));
    }

    @GetMapping("/edit/{id}/{name}/{phone}/{address}")
    public ResponseEntity<Boolean> updateSupplierById(@PathVariable int id, @PathVariable String name, @PathVariable String phone, @PathVariable String address) {
        return ResponseEntity.ok(cavesupplierService.updateSupplier(id, name, phone, address));
    }

}
