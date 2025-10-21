package com.example.stockapplication.service;

import com.example.stockapplication.entity.CaveHandler;
import com.example.stockapplication.repository.CaveHandlerRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class CaveHandlerService {

    private final CaveHandlerRepo caveHandlerRepo;

    public List<CaveHandler> getAllHandler() {      // ← renommé
        return caveHandlerRepo.findAll();
    }
}
