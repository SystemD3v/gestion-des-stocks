package com.example.stockapplication.entity;


import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Optional;

import static java.nio.channels.FileChannel.MapMode.READ_ONLY;

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
