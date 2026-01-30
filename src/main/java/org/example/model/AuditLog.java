package org.example.model;

import java.sql.Timestamp;

public class AuditLog {
    //getters and setters written for this model
    private String action;
    private int performedBy;
    private Timestamp createdAt;

    public AuditLog(String action, int performedBy, Timestamp createdAt) {
        this.action = action;
        this.performedBy = performedBy;
        this.createdAt = createdAt;
    }

    public String getAction() {
        return action;
    }

    public int getPerformedBy() {
        return performedBy;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }
}
