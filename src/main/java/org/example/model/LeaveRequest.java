package org.example.model;
import java.time.LocalDate;
public class LeaveRequest {
    //getters and setters written for this model
    private int leaveId;
    private String type;
    private String status;
    private int empId;
    private LocalDate fromDate;

    public String getType() {
        return type;
    }

    public LocalDate getToDate() {
        return toDate;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public void setToDate(LocalDate toDate) {
        this.toDate = toDate;
    }

    public LocalDate getFromDate() {
        return fromDate;
    }

    public void setFromDate(LocalDate fromDate) {
        this.fromDate = fromDate;
    }

    public String getStatus() {
        return status;
    }

    public int getEmpId() {
        return empId;
    }

    public void setEmpId(int empId) {
        this.empId = empId;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setType(String type) {
        this.type = type;
    }

    private LocalDate toDate;
    private String reason;

    public int getLeaveId() {
        return leaveId;
    }

    public void setLeaveId(int leaveId) {
        this.leaveId = leaveId;
    }

    public LeaveRequest(int leaveId, String type, String status) {
        this.leaveId = leaveId;
        this.type = type;
        this.status = status;
    }
}
