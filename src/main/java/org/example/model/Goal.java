package org.example.model;

import java.time.LocalDate;

public class Goal {
    private int goalId;
    private String description;
    private int progress;
    private int empId;

    public int getGoalId() {
        return goalId;
    }

    public void setGoalId(int goalId) {
        this.goalId = goalId;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public String getSuccessMetrics() {
        return successMetrics;
    }

    public void setSuccessMetrics(String successMetrics) {
        this.successMetrics = successMetrics;
    }

    public LocalDate getDeadline() {
        return deadline;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public void setDeadline(LocalDate deadline) {
        this.deadline = deadline;
    }

    public int getProgress() {
        return progress;
    }

    public int getEmpId() {
        return empId;
    }

    public void setEmpId(int empId) {
        this.empId = empId;
    }

    public void setProgress(int progress) {
        this.progress = progress;
    }

    private LocalDate deadline;
    private String priority;
    private String successMetrics;
    public Goal(int goalId, String description, int progress) {
        this.goalId = goalId;
        this.description = description;
        this.progress = progress;
    }
}
