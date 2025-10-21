package com.example.stockapplication.dto;

public class AddLogRequest {
    private String operation;
    private String description;

    public AddLogRequest() {}

    public AddLogRequest(String operation, String description) {
        this.operation = operation;
        this.description = description;
    }

    public String getOperation() {
        return operation;
    }

    public void setOperation(String operation) {
        this.operation = operation;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}