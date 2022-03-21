package com.revature.model;

import java.sql.Timestamp;

public class Reimbursement {
    private int id;
    private int amount;
    private Timestamp submitted;
    private Timestamp resolved;
    private String description;
    private Byte[] receipt;
    private int author;
    private int resolver;
    private int status;
    private ReimbursementType type;

    public enum ReimbursementType {
        // TODO add types
    }
}
