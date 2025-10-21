package com.example.stockapplication.service;

import com.example.stockapplication.entity.CaveUser;
import com.example.stockapplication.repository.CaveUserRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import com.example.stockapplication.dto.UserSummary;

import java.util.List;
import java.util.stream.Collectors;
@Service
@RequiredArgsConstructor
@Slf4j
public class CaveUserService {

    private final CaveUserRepo caveuserRepo;

    public List<CaveUser> getAllUsers() {
        return caveuserRepo.findAll();
    }

    public List<UserSummary> getUserByLastname(String lastname) {
        return caveuserRepo.findAllByLastname(lastname)
                .stream()
                .map(CaveUser -> new UserSummary(
                        CaveUser.getLastname(),
                        CaveUser.getFirstname(),
                        CaveUser.getTotal_bottles_bought(),
                        CaveUser.getEmail()

                ))
                .collect(Collectors.toList());
    }

    public void deleteById(Integer id) {

    }


    public CaveUser createCaveUser(CaveUser caveUser) {
        return caveuserRepo.save(caveUser);
    }


}
