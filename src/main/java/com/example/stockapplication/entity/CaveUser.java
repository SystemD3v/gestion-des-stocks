package com.example.stockapplication.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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
    private String lastname;
    private String firstname;
    private Integer total_bottles_bought;
    private String email;
    private String password;
    private String role;
    private String address;
    private String phone_number;
}
