package com.revature.model;

import java.io.InputStream;
import java.sql.Timestamp;
import java.util.Arrays;
import java.util.Objects;

public class Reimbursement {
    private int id;
    private int amount;
    private Timestamp submitted;
    private Timestamp resolved;
    private String description;
    private byte[] image;
    private int author;
    private int resolver;
    private int status;
    private String reimb_type;

    @Override
    public String toString() {
        return "Reimbursement{" +
                "id=" + id +
                ", amount=" + amount +
                ", submitted=" + submitted +
                ", resolved=" + resolved +
                ", description='" + description + '\'' +
                ", image=" + Arrays.toString(image) +
                ", author=" + author +
                ", resolver=" + resolver +
                ", status=" + status +
                ", reimb_type='" + reimb_type + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Reimbursement)) return false;
        Reimbursement that = (Reimbursement) o;
        return getId() == that.getId() && getAmount() == that.getAmount() && getAuthor() == that.getAuthor() && getResolver() == that.getResolver() && getStatus() == that.getStatus() && getSubmitted().equals(that.getSubmitted()) && Objects.equals(getResolved(), that.getResolved()) && Objects.equals(getDescription(), that.getDescription()) && Arrays.equals(getImage(), that.getImage()) && getReimb_type().equals(that.getReimb_type());
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(getId(), getAmount(), getSubmitted(), getResolved(), getDescription(), getAuthor(), getResolver(), getStatus(), getReimb_type());
        result = 31 * result + Arrays.hashCode(getImage());
        return result;
    }

    public Reimbursement(int id, int amount, Timestamp submitted, String description, byte[] image, int author, int resolver, int status, String reimb_type) {
        this.id = id;
        this.amount = amount;
        this.submitted = submitted;
        this.description = description;
        this.image = image;
        this.author = author;
        this.resolver = resolver;
        this.status = status;
        this.reimb_type = reimb_type;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public Timestamp getSubmitted() {
        return submitted;
    }

    public void setSubmitted(Timestamp submitted) {
        this.submitted = submitted;
    }

    public Timestamp getResolved() {
        return resolved;
    }

    public void setResolved(Timestamp resolved) {
        this.resolved = resolved;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public int getAuthor() {
        return author;
    }

    public void setAuthor(int author) {
        this.author = author;
    }

    public int getResolver() {
        return resolver;
    }

    public void setResolver(int resolver) {
        this.resolver = resolver;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getReimb_type() {
        return reimb_type;
    }

    public void setReimb_type(String reimb_type) {
        this.reimb_type = reimb_type;
    }
}
