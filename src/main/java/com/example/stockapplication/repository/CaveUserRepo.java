package com.example.stockapplication.repository;

import com.example.stockapplication.entity.CaveUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CaveUserRepo extends JpaRepository<CaveUser, Integer> {
    @Query(value = "SELECT '*' FROM cave_users WHERE lastName")
    List<CaveUser> findByLastName(String lastName);
}
