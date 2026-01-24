package org.example.model;

public class PerformanceReview {
    private int empId;
    private String deliverables;
    private String accomplishments;
    private int selfRating;

    public int getEmpId() {
        return empId;
    }

    public String getAccomplishments() {
        return accomplishments;
    }

    public int getSelfRating() {
        return selfRating;
    }

    public void setSelfRating(int selfRating) {
        this.selfRating = selfRating;
    }

    public void setAccomplishments(String accomplishments) {
        this.accomplishments = accomplishments;
    }

    public String getDeliverables() {
        return deliverables;
    }

    public void setDeliverables(String deliverables) {
        this.deliverables = deliverables;
    }

    public void setEmpId(int empId) {
        this.empId = empId;
    }
// getters & setters
}
