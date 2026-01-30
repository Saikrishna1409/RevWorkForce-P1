package org.example.service;

import org.example.DAO.*;

import java.util.Scanner;

public class AdminService {

    private final EmployeeDAO empDao = new EmployeeDAO();
    private final LeaveDAO leaveDao = new LeaveDAO();
    private final DepartmentDAO departmentDao = new DepartmentDAO();
    private final PerformanceDAO performanceDao = new PerformanceDAO();
    private final AuditDAO auditDao = new AuditDAO();
    private final AuthService authService = new AuthService();

    // EMPLOYEE MANAGEMENT
    public boolean addEmployee(int id, String name, String email, String designation, int managerId) {
        return empDao.addEmployee(id, name, email, designation, managerId);
    }

    public boolean updateEmployee(int id, String email, String designation) {
        return empDao.updateEmployee(id, email, designation);
    }

    public String viewEmployees(Scanner sc) {
        return empDao.viewAllEmployees(); // updated DAO should return String
    }

    public boolean updateStatus(int id, boolean active) {
        return empDao.updateStatus(id, active);
    }

    public boolean assignManager(int empId, int managerId) {
        return empDao.assignManager(empId, managerId);
    }

    public void resetPassword(int id, String pwd) {
        authService.resetPassword(id, pwd);
    }

    // LEAVE MANAGEMENT
    public boolean configureLeave(String type, int quota) {
        return leaveDao.addOrUpdateLeaveType(type, quota);
    }

    public boolean adjustLeave(int empId, String type, int days) {
        return leaveDao.adjustLeaveBalance(empId, type, days);
    }

    public boolean addHoliday(String date, String name) {
        return leaveDao.addHoliday(date, name);
    }

    // DEPARTMENT MANAGEMENT
    public boolean addDepartment(String name) {
        return departmentDao.addDepartment(name);
    }

    // PERFORMANCE MANAGEMENT
    public boolean addReviewCycle(String name, String start, String end) {
        return performanceDao.addReviewCycle(name, start, end);
    }

    // AUDIT LOGS
    public String viewAuditLogs() {
        return auditDao.fetchLogs().toString(); // updated DAO should return String
    }
}
