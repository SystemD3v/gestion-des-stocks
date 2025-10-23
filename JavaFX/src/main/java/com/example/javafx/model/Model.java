package com.example.javafx.model;

public class Model {

    public class stock{
        public int id;
        public String label;
        public int years;
        public String genre;
        public String area;
        public int available_quantity;
        public double price;
        public int supplier_id;
    }

    public class instance{
        public Integer id;
        public Integer supplyGroupId;
        public Integer requestAmount;
        public Integer operation;
        public boolean validated;
        public boolean completed;
        public String orderTimestamp;
        public Integer stockId;
        public Integer supplierId;
        public Integer userId;
    }

    public class supplier{
        public Integer id;
        public String supplier_name;
        public String supplier_address;
        public Integer supplier_phone;
    }

    public class log{
        public Integer logId;
        public String operation;
        public String description;
    }

    public class user{
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
