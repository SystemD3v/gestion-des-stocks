package com.example.stockapplication.repository;

import com.example.stockapplication.entity.CaveUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CaveUserRepo extends JpaRepository<CaveUser, Integer> {
    @Override
    void deleteById(Integer id);

    List<CaveUser> findCaveUserById(Integer id);

    List<CaveUser> findAllByLastname(String lastname);
}
