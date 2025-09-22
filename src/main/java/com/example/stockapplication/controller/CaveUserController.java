package com.example.stockapplication.controller;

import com.example.stockapplication.entity.CaveUser;
import com.example.stockapplication.service.CaveUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users/v1")
@RequiredArgsConstructor
public class CaveUserController {

    private final CaveUserService caveuserService; // final + RequiredArgsConstructor

    @GetMapping("/")
    public ResponseEntity<List<CaveUser>> getAllUsers() {
        return ResponseEntity.ok(caveuserService.getAllUsers());
    }
}
