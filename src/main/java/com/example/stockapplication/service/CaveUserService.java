package com.example.stockapplication.service;

import com.example.stockapplication.entity.CaveUser;
import com.example.stockapplication.repository.CaveUserRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class CaveUserService {

    private final CaveUserRepo caveuserRepo;

    public List<CaveUser> getAllUsers() {
        return caveuserRepo.findAll();
    }

    public List<CaveUser> getUsersByLastName(String lastName) {
        return caveuserRepo.findByLastName(lastName);
    }


}