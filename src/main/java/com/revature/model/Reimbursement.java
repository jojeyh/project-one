package com.revature.model;

import java.io.InputStream;
import java.sql.Timestamp;
import java.util.Objects;

public class Reimbursement {
    private int reimb_id;
    private int reimb_amount;
    private Timestamp reimb_submitted;
    private Timestamp reimb_resolved;
    private String reimb_description;
    private InputStream reimb_receipt;
    private int reimb_author;
    private int reimb_resolver;
    private int reimb_status_id;
    private int reimb_type_id;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Reimbursement)) return false;
        Reimbursement that = (Reimbursement) o;
        return getReimb_id() == that.getReimb_id() && getReimb_amount() == that.getReimb_amount() && getReimb_author() == that.getReimb_author() && getReimb_resolver() == that.getReimb_resolver() && getReimb_status_id() == that.getReimb_status_id() && getReimb_type_id() == that.getReimb_type_id() && getReimb_submitted().equals(that.getReimb_submitted()) && Objects.equals(getReimb_resolved(), that.getReimb_resolved()) && Objects.equals(getReimb_description(), that.getReimb_description()) && Objects.equals(getReimb_receipt(), that.getReimb_receipt());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getReimb_id(), getReimb_amount(), getReimb_submitted(), getReimb_resolved(), getReimb_description(), getReimb_receipt(), getReimb_author(), getReimb_resolver(), getReimb_status_id(), getReimb_type_id());
    }

    public int getReimb_id() {
        return reimb_id;
    }

    public void setReimb_id(int reimb_id) {
        this.reimb_id = reimb_id;
    }

    public int getReimb_amount() {
        return reimb_amount;
    }

    public void setReimb_amount(int reimb_amount) {
        this.reimb_amount = reimb_amount;
    }

    public Timestamp getReimb_submitted() {
        return reimb_submitted;
    }

    public void setReimb_submitted(Timestamp reimb_submitted) {
        this.reimb_submitted = reimb_submitted;
    }

    public Timestamp getReimb_resolved() {
        return reimb_resolved;
    }

    public void setReimb_resolved(Timestamp reimb_resolved) {
        this.reimb_resolved = reimb_resolved;
    }

    public String getReimb_description() {
        return reimb_description;
    }

    public void setReimb_description(String reimb_description) {
        this.reimb_description = reimb_description;
    }

    public InputStream getReimb_receipt() {
        return reimb_receipt;
    }

    public void setReimb_receipt(InputStream reimb_receipt) {
        this.reimb_receipt = reimb_receipt;
    }

    public int getReimb_author() {
        return reimb_author;
    }

    public void setReimb_author(int reimb_author) {
        this.reimb_author = reimb_author;
    }

    public int getReimb_resolver() {
        return reimb_resolver;
    }

    public void setReimb_resolver(int reimb_resolver) {
        this.reimb_resolver = reimb_resolver;
    }

    public int getReimb_status_id() {
        return reimb_status_id;
    }

    public void setReimb_status_id(int reimb_status_id) {
        this.reimb_status_id = reimb_status_id;
    }

    public int getReimb_type_id() {
        return reimb_type_id;
    }

    public void setReimb_type_id(int reimb_type_id) {
        this.reimb_type_id = reimb_type_id;
    }
}
