package com.example.javafx.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

public class Model {

    @Getter
    @AllArgsConstructor
    public static class stock{
        public int id;
        public String label;
        public int years;
        public String genre;
        public String area;
        public int available_quantity;
        public double price;
        public int supplier_id;
    }

    @Getter
    @AllArgsConstructor
    public static class Instance{

        public Integer id;
        public Integer supplyGroupId;
        public Integer requestAmount;
        public Integer operation;
        public boolean validated;
        public String completed;
        public String orderTimestamp;
        public String stockId;
        public String supplierId;
        public Integer userId;
        public String stockName;
        public String supplierName;

        public Instance() {

        }
    }

    @Getter
    @AllArgsConstructor
    public static class supplier{
        public Integer id;
        public String supplier_name;
        public String supplier_address;
        public Integer supplier_phone;
    }

    @Getter
    @AllArgsConstructor
    public static class log{
        public Integer logId;
        public String operation;
        public String description;
    }

    @Getter
    @AllArgsConstructor
    public static class user{
        public Integer id;
        public String lastname;
        public String firstname;
        public Integer total_bottles_bought;
        public String email;
        public String password;
        public String role;
        public String address;
        public String phone_number;
    }

}
