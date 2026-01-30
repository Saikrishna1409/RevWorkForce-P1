package org.example.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.DAO.EmployeeDAO;
import org.example.DAO.LeaveDAO;
import org.example.DAO.PerformanceDAO;
import org.example.DAO.NotificationDAO;
import org.example.DAO.GoalDAO;

// Service layer for all employee-related operations
// Coordinates between controllers and multiple DAOs
public class EmployeeService {
    private static final Logger logger =
            LogManager.getLogger(EmployeeService.class);

    // DAO responsible for employee profile and core employee data
    private final EmployeeDAO empDao = new EmployeeDAO();

    // DAO responsible for leave-related operations
    private final LeaveDAO leaveDao = new LeaveDAO();

    // DAO responsible for performance review operations
    private final PerformanceDAO perfDao = new PerformanceDAO();

    // DAO responsible for employee notifications
    private final NotificationDAO notifDao = new NotificationDAO();

    // DAO responsible for employee goals
    private final GoalDAO goalDao = new GoalDAO();

    // Fetches and displays employee profile details
    public void viewProfile(int empId) {
        logger.info("Viewing profile for empId={}", empId);
        String msg = empDao.viewProfile(empId);
        System.out.println(msg);
    }

    // Applies leave for the given employee and displays result message
    public void applyLeave(int empId, String from, String to, String type, String reason) {
        String msg = leaveDao.applyLeave(empId, from, to, type, reason);
        System.out.println(msg);
        logger.info("Leave applied by empId={}, from={} to={}", empId, from, to);
    }

    // Displays all leaves applied by the employee
    public void viewLeaves(int empId) {
        String msg = leaveDao.viewLeaves(empId);
        System.out.println(msg);
        logger.info("Viewing leaves for empId={}", empId);
    }

    // Submits employee self-performance review details
    public void submitReview(int empId, String del, String ach, int rating) {
        logger.info("Performance review submitted by empId={}", empId);
        String msg = perfDao.submitReview(empId, del, ach, rating);
        System.out.println(msg);
    }

    // Fetches and displays employee notifications
    public void viewNotifications(int empId) {
        logger.info("Viewing notifications for empId={}", empId);
        String msg = notifDao.view(empId);
        System.out.println(msg);
    }

    // Displays goals assigned to the employee
    public void viewGoals(int empId) throws Exception {
        logger.info("Viewing goals for empId={}", empId);
        String msg = goalDao.viewGoals(empId);
        System.out.println(msg);
    }

    // Updates employee profile information
    public boolean updateProfile(int empId, String phone, String address, String emergency) {
        // Returns true if update is successful
        logger.info("Updating profile for empId={}", empId);
        boolean success = empDao.updateProfile(empId, phone, address, emergency);
        return success;
    }

    // Displays reporting manager details for the employee
    public boolean viewReportingManager(int empId) {
        logger.info("Viewing reporting manager for empId={}", empId);
        String msg = empDao.viewReportingManager(empId);

        // If no manager is assigned, return false
        if (msg == null) return false;

        System.out.println(msg);
        return true;
    }

    // Displays applied leaves of the employee
    public boolean viewAppliedLeaves(int empId) {

        String msg = empDao.viewAppliedLeaves(empId);

        // If no leave records exist, return false
        if (msg == null) return false;

        System.out.println(msg);
        return true;
    }

    // Cancels a pending leave request for the employee
    public boolean cancelLeave(int empId, int leaveId) {
        logger.info("Cancel leave requested: empId={}, leaveId={}", empId, leaveId);
        return empDao.cancelPendingLeave(empId, leaveId);
    }

    // Displays company holiday calendar
    public boolean viewHolidayCalendar() {
        String msg = empDao.viewHolidayCalendar();
        logger.info("Viewing holiday calendar");
        // If no holidays are configured, return false
        if (msg == null) return false;

        System.out.println(msg);
        return true;
    }

    // Adds a new goal for the employee
    public boolean addGoal(int empId, String desc, String deadline, String priority, String metrics) {
        return empDao.addGoal(empId, desc, deadline, priority, metrics);
    }
}
