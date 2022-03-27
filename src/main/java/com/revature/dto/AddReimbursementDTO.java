package com.revature.dto;

import java.io.InputStream;
import java.sql.Timestamp;

public class AddReimbursementDTO {
    private int userId;
    private String description;
    private int amount;
    private InputStream receipt;
    private Timestamp submitted;
    private String type;

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Timestamp getSubmitted() {
        return submitted;
    }

    public void setSubmitted(Timestamp submitted) {
        this.submitted = submitted;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public InputStream getReceipt() {
        return receipt;
    }

    public void setReceipt(InputStream receipt) {
        this.receipt = receipt;
    }
}
