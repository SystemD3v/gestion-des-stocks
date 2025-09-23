package com.example.stockapplication.service;

import com.example.stockapplication.entity.CaveController;
import com.example.stockapplication.repository.CaveControllerRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class CaveControllerService {

    private final CaveControllerRepo cavecontrollerRepo;

    public List<CaveController> getAllController() {      // ← renommé
        return cavecontrollerRepo.findAll();
    }
}
