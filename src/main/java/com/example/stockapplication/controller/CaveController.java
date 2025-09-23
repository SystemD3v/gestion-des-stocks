package com.example.stockapplication.controller;

import com.example.stockapplication.entity.*;
import com.example.stockapplication.service.*;
import lombok.RequiredArgsConstructor;
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

    @GetMapping("/get_logs")
    public ResponseEntity<List<CaveLogs>> getAllLogs() {
        return ResponseEntity.ok(cavelogsService.getAllLogs());
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

    private final CaveControllerService cavecontrollerService; // final + RequiredArgsConstructor

    @GetMapping("/get_controller")
    public ResponseEntity<List<com.example.stockapplication.entity.CaveController>> getAllController() {
        return ResponseEntity.ok(cavecontrollerService.getAllController());
    }

    @GetMapping("/get_user_by_lastname")
    public ResponseEntity<List<CaveUser>> getUserByLastName(@RequestParam("name") String name) {
        return ResponseEntity.ok(caveuserService.getUsersByLastName(name));
    }
}
