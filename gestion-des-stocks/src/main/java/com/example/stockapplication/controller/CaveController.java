package com.example.stockapplication.controller;

import com.example.stockapplication.entity.*;
import com.example.stockapplication.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
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

    @GetMapping("/getInstanceById/{id}")
    public ResponseEntity<Optional<CaveHandler>> getInstanceById(@PathVariable Integer id){
        return ResponseEntity.ok(caveHandlerService.findById(id));
    }

    @GetMapping("/getInstanceByTimestamp/{firstTimestamp}/{secondTimestamp}")
    public ResponseEntity<List<CaveHandler>> getInstanceByTimestamp(@PathVariable String firstTimestamp, @PathVariable String secondTimestamp){
        return ResponseEntity.ok(caveHandlerService.findByOrderTimestampBetween(firstTimestamp, secondTimestamp));
    }

    @GetMapping("/getInstanceByUserId/{id}")
    public ResponseEntity<List<CaveHandler>> getInstanceByUserId(@PathVariable Integer id){
        return ResponseEntity.ok(caveHandlerService.findByUserId(id));
    }

    @GetMapping("/getInstanceByStockId/{id}")
    public ResponseEntity<List<CaveHandler>> getInstanceByStockId(@PathVariable Integer id){
        return ResponseEntity.ok(caveHandlerService.findByStockId(id));
    }

    @GetMapping("/getInstanceByValidation/{bool}")
    public ResponseEntity<List<CaveHandler>> getInstanceByValidation(@PathVariable boolean bool){
        return ResponseEntity.ok(caveHandlerService.findByValidated(bool));
    }

    @GetMapping("/getInstanceByComplete/{bool}")
    public ResponseEntity<List<CaveHandler>> getInstanceByComplete(@PathVariable boolean bool){
        return ResponseEntity.ok(caveHandlerService.findByCompleted(bool));
    }

    @GetMapping("/getLastInstance")
    public ResponseEntity<List<CaveHandler>> getLastHandler() {
        return ResponseEntity.ok(caveHandlerService.getLastHandler());
    }

    @DeleteMapping("/deleteInstance/{id}")
    public String deleteInstanceById(@PathVariable Integer id){
        caveHandlerService.deleteHandlerById(id);

        return "Deleted";
    }

    @PostMapping("/createInstance")
    public ResponseEntity<String> createInstance(@RequestBody CaveHandler caveHandler) {
        CaveHandler newInstance = caveHandlerService.createInstance(caveHandler);
        return ResponseEntity
                .created(URI.create("/api/v1/handler/" + newInstance.getId()))
                .body("Instance created successfully");
    }

    @GetMapping("/updateInstance/{id}/{var}/{value}")
    public void updateInstance(@PathVariable Integer id, @PathVariable String var, @PathVariable String value){
        caveHandlerService.update(id,var,value);
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

    @GetMapping("/get_user_by_id/{id}")
    public ResponseEntity<List<CaveUser>> getUserById(@PathVariable Integer id){
        return ResponseEntity.ok(caveuserService.getUserById(id));
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

    @GetMapping("/updateUser/{id}/{var}/{value}")
    public void updateUser(@PathVariable Integer id, @PathVariable String var, @PathVariable String value){
        caveuserService.updateUser(id,var,value);
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
    public ResponseEntity<Optional<CaveSupplier>> getCaveSupplierByid(@PathVariable int id) {
        return ResponseEntity.ok(cavesupplierService.getCaveSupplierByid(id));
    }

    @GetMapping("/edit_supplier/{id}/{name}/{phone}/{address}")
    public void updateSupplierById(@PathVariable int id, @PathVariable String name, @PathVariable int phone, @PathVariable String address) {
        cavesupplierService.updatesupplier(id, name, phone, address);
    }

    @PostMapping("/create_supplier")
    public ResponseEntity<String> createSupp(@RequestBody CaveSupplier caveSupplier) {
        CaveSupplier createdSupp = cavesupplierService.createSupp(caveSupplier);
        return ResponseEntity
                .created(URI.create("/api/v1/users/" + createdSupp.getId()))
                .body("User created successfully");
    }
    @DeleteMapping("/sup_supplier/{id}")          //<---        TO DO LATER IF THERE'S TIME DONT TRY FOR NOW!!!!
    public String deleteById(@PathVariable int id) {
        cavesupplierService.deleteById(id);
        return "deleted";
    }

    @GetMapping("/test")
    public String test(){
        return "test";
    }
}
