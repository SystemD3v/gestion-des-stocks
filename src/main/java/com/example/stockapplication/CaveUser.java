package com.example.stockapplication;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "cave_client")

public class CaveUser {

    @Id
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    @Column(name = "user_id")

    private Integer id;
    private String firstname;
    private String lastname;
    private String total_bottle_bought;
    private String email;
    private String password;
    private String role;

}
