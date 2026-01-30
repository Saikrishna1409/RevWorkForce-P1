package org.example.model;

import java.time.LocalDate;

public class Employee {
    //getters and setters written for this model
    private int id;
    private String name;
    private String role;
    private int managerId;
    private int departmentId;
    private String password;
    private String address;
private String emergency;

    public String getEmergency() {
        return emergency;
    }

    public void setEmergency(String emergency) {
        this.emergency = emergency;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return Phone;
    }

    public void setPhone(String phone) {
        Phone = phone;
    }

    private String Phone;
    private LocalDate doj;
    private int designationId;


    public Employee(int id, String name, String role, int managerId) {
        this.id = id;
        this.name = name;
        this.role = role;
        this.managerId = managerId;
    }

    public int getId() { return id; }
    public String getName() { return name; }
    public String getRole() { return role; }
    public int getManagerId() { return managerId; }
    public int getDesignationId() {
        return designationId;
    }
    public int getDepartmentId() {
        return departmentId;
    }
    public String getPassword() {
        return password;
    }
    public LocalDate getDoj() {
        return doj;
    }


}
