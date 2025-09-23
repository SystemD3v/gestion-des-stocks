package com.example.stockapplication.repository;

import com.example.stockapplication.entity.CaveUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface CaveUserRepo extends JpaRepository<CaveUser, Integer> {
    @Query(value = "SELECT total_bottles_bought, email FROM cave_users WHERE lastname = :lastname", nativeQuery = true)
    List<String> getTotalsByLastName(@Param("lastname") String lastname);

//    @Modifying
//    @Transactional
//    @Query(value = "INSERT INTO cave_users(firstname,lastname,email,adresse,telephone) VALUES (:fisrtname, :lastname, :email,:adress,:phonenulber)", nativeQuery = true)
//    List<String> getTotalsByLastName(@Param("lastname") String lastname);



}
