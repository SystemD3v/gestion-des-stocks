package com.example.stockapplication.dto;

public record UserSummary(
        String lastname,
        String firstname,
        Integer total_bottles_bought,
        String email
) {}
