package org.example.service;

import org.example.DAO.ManagerDAO;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

// Service layer handling all manager-related business operations
// Acts as a bridge between ManagerController and ManagerDao
public class ManagerService {
    private static final Logger logger =
            LogManager.getLogger(ManagerService.class);
    // DAO responsible for all manager-specific database operations
    private final ManagerDAO managerDao = new ManagerDAO();

    // Fetches and displays all direct reportees under the manager
    public void viewReportees(int managerId) {
        logger.info("Viewing reportees for managerId={}", managerId);
        managerDao.viewReportees(managerId);
    }

    // Fetches and displays all leave requests from the manager's team
    public void viewTeamLeaveRequests(int managerId) {
        logger.info("Viewing team leave requests for managerId={}", managerId);
        managerDao.viewTeamLeaveRequests(managerId);
    }

    // Approves or rejects a leave request with manager comments
    public boolean updateLeaveStatus(int leaveId, String status, String comments) {
        logger.info("Updating leave status: leaveId={}, status={}", leaveId, status);
        return managerDao.updateLeaveStatus(leaveId, status, comments);
    }

    // Displays leave balance summary for the manager’s team
    public void viewTeamLeaveSummary(int managerId) {
        logger.info("Viewing team leave summary for managerId={}", managerId);
        managerDao.viewTeamLeaveSummary(managerId);
    }

    // Displays performance-related documents of team members
    public void viewPerformanceDocs(int managerId) {
        logger.info("Viewing performance docs for managerId={}", managerId);
        managerDao.viewPerformanceDocs(managerId);
    }

    // Adds qualitative feedback for an employee
    public boolean addFeedback(int empId, String feedback) {
        return managerDao.addFeedback(empId, feedback);
    }

    // Rates an employee’s performance numerically
    public boolean rateEmployee(int empId, int rating) {
        return managerDao.rateEmployee(empId, rating);
    }

    // Displays goals assigned to employees under the manager
    public void viewEmployeeGoals(int managerId) {
        managerDao.viewEmployeeGoals(managerId);
    }

    // Tracks current status of employee goals
    public void trackGoalStatus(int managerId) {
        managerDao.trackGoalStatus(managerId);
    }

    // Displays the hierarchical structure of the manager’s team
    public void viewTeamHierarchy(int managerId) {
        managerDao.viewTeamHierarchy(managerId);
    }

    // Displays attendance summary of all team members
    public void viewAttendanceSummary(int managerId) {
        managerDao.viewAttendanceSummary(managerId);
    }

    // Generates a consolidated performance report for the team
    public void generatePerformanceReport(int managerId) {
        managerDao.generatePerformanceReport(managerId);
    }

    // Displays notifications relevant to the manager
    public void viewNotifications(int managerId) {
        managerDao.viewNotifications(managerId);
    }
}