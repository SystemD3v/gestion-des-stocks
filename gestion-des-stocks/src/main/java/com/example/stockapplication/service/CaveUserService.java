package com.example.stockapplication.service;

import com.example.stockapplication.entity.CaveStock;
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

    public void updateUser(Integer id, String var, String value){

        CaveUser caveUser = caveuserRepo.findById(id).get();
        switch (var) {
            case "lastname":
                caveUser.setLastname(value);
                break;
            case "firstname":
                caveUser.setFirstname(value);
                break;
            case "email":
                caveUser.setEmail(value);
                break;
            case "password":
                caveUser.setPassword(value);
                break;
            case "role":
                caveUser.setRole(value);
                break;
            case "address":
                caveUser.setAddress(value);
                break;
            case "phone_number":
                caveUser.setPhone_number(value);
                break;
            default:
                break;
        };

        caveuserRepo.save(caveUser);
    }


    public CaveUser createCaveUser(CaveUser caveUser) {
        return caveuserRepo.save(caveUser);
    }


}
