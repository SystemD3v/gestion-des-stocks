package com.example.stockapplication.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Optional;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "cave_users")

public class CaveUser {

    @Id
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Integer id;

    @Column(name = "lastname")
    private String lastName;

    private String firstname;
    private String total_bottle_bought;
    private String email;
    private String password;
    private String role;



}
