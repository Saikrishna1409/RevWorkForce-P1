package org.example.model;

public class Notification {
    private int empId;
    private String message;
    private boolean isRead;

    public int getEmpId() {
        return empId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isRead() {
        return isRead;
    }

    public void setRead(boolean read) {
        isRead = read;
    }

    public void setEmpId(int empId) {
        this.empId = empId;
    }
// getters & setters
}
