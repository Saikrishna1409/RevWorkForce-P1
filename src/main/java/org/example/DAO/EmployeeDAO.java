package org.example.DAO;

import org.example.util.DBUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;

/**
 * DAO class for Employee operations.
 * Handles all database interactions related to employees, leaves, goals, and holidays.
 */
public class EmployeeDAO {

    // Logger instance for logging info and errors
    private static final Logger logger = LogManager.getLogger(EmployeeDAO.class);

    /**
     * Fetches the profile of an employee by ID.
     *
     * @param empId Employee ID
     * @return Formatted profile string or error message
     */
    public String viewProfile(int empId) {
        String sql = "SELECT * FROM employees WHERE emp_id=?";
        try (Connection con = DBUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, empId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    String result = String.format(
                            "Name: %s | Phone: %s | Department: %s",
                            rs.getString("name"),
                            rs.getString("phone"),
                            rs.getString("department")
                    );
                    logger.info("Viewed profile for empId={}", empId);
                    return result;
                } else {
                    return "❌ Employee not found";
                }
            }
        } catch (SQLException e) {
            logger.error("Error viewing profile for empId={}", empId, e);
            return "❌ Error fetching employee profile";
        }
    }

    /**
     * Updates an employee's phone, address, and emergency contact.
     *
     * @param empId     Employee ID
     * @param phone     Phone number
     * @param address   Address
     * @param emergency Emergency contact
     * @return true if update succeeded, false otherwise
     */
    public boolean updateProfile(int empId, String phone, String address, String emergency) {
        String sql = "UPDATE employees SET phone=?, address=?, emergency_contact=? WHERE emp_id=?";
        try (Connection con = DBUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, phone);
            ps.setString(2, address);
            ps.setString(3, emergency);
            ps.setInt(4, empId);

            int rows = ps.executeUpdate();
            logger.info("Updated profile for empId={}, rows affected={}", empId, rows);
            return rows > 0;
        } catch (SQLException e) {
            logger.error("Error updating profile for empId={}", empId, e);
            return false;
        }
    }

    /**
     * Fetches the reporting manager for a given employee.
     *
     * @param empId Employee ID
     * @return Formatted string of manager info or null if not assigned
     */
    public String viewReportingManager(int empId) {
        String sql = """
                SELECT m.emp_id, m.name, m.designation, m.department
                FROM employees e
                JOIN employees m ON e.manager_id = m.emp_id
                WHERE e.emp_id = ?
                """;
        try (Connection con = DBUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, empId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return String.format(
                            "ID: %d | Name: %s | Designation: %s | Department: %s",
                            rs.getInt("emp_id"),
                            rs.getString("name"),
                            rs.getString("designation"),
                            rs.getString("department")
                    );
                } else {
                    return null; // Controller will handle null
                }
            }
        } catch (SQLException e) {
            logger.error("Error fetching reporting manager for empId={}", empId, e);
            return "❌ Error fetching reporting manager";
        }
    }

    /**
     * Adds a new employee.
     *
     * @param empId      Employee ID
     * @param name       Name
     * @param email      Email
     * @param designation Designation
     * @param managerId  Manager ID
     * @return true if added successfully
     */
    public boolean addEmployee(int empId, String name, String email, String designation, int managerId) {
        String sql = """
            INSERT INTO employees
            (emp_id, name, email, designation, manager_id, active)
            VALUES (?, ?, ?, ?, ?, 1)
            """;

        try (Connection con = DBUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, empId);
            ps.setString(2, name);
            ps.setString(3, email);
            ps.setString(4, designation);
            ps.setInt(5, managerId);

            int rows = ps.executeUpdate();
            logger.info("Added employee empId={}, rows affected={}", empId, rows);
            return rows > 0;

        } catch (SQLException e) {
            logger.error("Error adding employee empId={}", empId, e);
            return false;
        }
    }

    /**
     * Updates employee email and designation.
     */
    public boolean updateEmployee(int empId, String email, String designation) {
        String sql = "UPDATE employees SET email=?, designation=? WHERE emp_id=?";

        try (Connection con = DBUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, email);
            ps.setString(2, designation);
            ps.setInt(3, empId);

            int rows = ps.executeUpdate();
            logger.info("Updated employee empId={}, rows affected={}", empId, rows);
            return rows > 0;

        } catch (SQLException e) {
            logger.error("Error updating employee empId={}", empId, e);
            return false;
        }
    }

    /**
     * Fetches leave applications for an employee.
     */
    public String viewAppliedLeaves(int empId) {
        String sql = """
                SELECT leave_id, type, from_date, to_date, status, manager_comment
                FROM leave_requests
                WHERE emp_id = ?
                ORDER BY leave_id DESC
                """;
        StringBuilder sb = new StringBuilder();
        sb.append("===== Your Leave Applications =====\n")
                .append("ID | Type | From | To | Status | Manager Comments\n")
                .append("--------------------------------------------------\n");

        try (Connection con = DBUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, empId);
            try (ResultSet rs = ps.executeQuery()) {
                boolean hasData = false;
                while (rs.next()) {
                    hasData = true;
                    sb.append(String.format(
                            "%d | %s | %s | %s | %s | %s%n",
                            rs.getInt("leave_id"),
                            rs.getString("type"),
                            rs.getDate("from_date"),
                            rs.getDate("to_date"),
                            rs.getString("status"),
                            rs.getString("manager_comment") == null ? "-" : rs.getString("manager_comment")
                    ));
                }
                if (!hasData) return null; // Controller will handle null
            }
            return sb.toString();
        } catch (SQLException e) {
            logger.error("Error fetching leaves for empId={}", empId, e);
            return "❌ Error fetching leaves";
        }
    }

    /**
     * Returns a formatted list of all employees.
     */
    public String viewAllEmployees() {
        String sql = "SELECT emp_id, name, email, designation, active FROM employees";
        StringBuilder sb = new StringBuilder();
        sb.append("===== Employees =====\nID | Name | Email | Designation | Status\n-----------------------------------------\n");

        try (Connection con = DBUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            boolean hasData = false;
            while (rs.next()) {
                hasData = true;
                sb.append(String.format("%d | %s | %s | %s | %s%n",
                        rs.getInt("emp_id"),
                        rs.getString("name"),
                        rs.getString("email"),
                        rs.getString("designation"),
                        rs.getBoolean("active") ? "Active" : "Inactive"
                ));
            }

            if (!hasData) return null; // Controller handles null
            return sb.toString();

        } catch (SQLException e) {
            logger.error("Error fetching all employees", e);
            return "❌ Error fetching employees";
        }
    }

    /**
     * Updates the active/inactive status of an employee.
     */
    public boolean updateStatus(int empId, boolean active) {
        String sql = "UPDATE employees SET active=? WHERE emp_id=?";

        try (Connection con = DBUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setBoolean(1, active);
            ps.setInt(2, empId);

            int rows = ps.executeUpdate();
            logger.info("Updated status for empId={}, rows affected={}", empId, rows);
            return rows > 0;

        } catch (SQLException e) {
            logger.error("Error updating status for empId={}", empId, e);
            return false;
        }
    }

    /**
     * Cancels a pending leave application.
     */
    public boolean cancelPendingLeave(int empId, int leaveId) {
        String sql = """
                DELETE FROM leave_requests
                WHERE leave_id = ? AND emp_id = ? AND status = 'PENDING'
                """;
        try (Connection con = DBUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, leaveId);
            ps.setInt(2, empId);
            int rows = ps.executeUpdate();
            logger.info("Cancelled pending leave leaveId={}, empId={}, rows affected={}", leaveId, empId, rows);
            return rows > 0;
        } catch (SQLException e) {
            logger.error("Error cancelling leave leaveId={}, empId={}", leaveId, empId, e);
            return false;
        }
    }

    /**
     * Fetches company holiday calendar.
     */
    public String viewHolidayCalendar() {
        String sql = "SELECT holiday_date, holiday_name FROM holidays ORDER BY holiday_date";
        StringBuilder sb = new StringBuilder();
        sb.append("===== Company Holiday Calendar =====\n")
                .append("Date | Holiday\n")
                .append("------------------------------\n");

        try (Connection con = DBUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            boolean hasData = false;
            while (rs.next()) {
                hasData = true;
                sb.append(String.format("%s | %s%n",
                        rs.getDate("holiday_date"),
                        rs.getString("holiday_name")));
            }
            if (!hasData) return null;
            return sb.toString();
        } catch (SQLException e) {
            logger.error("Error fetching holiday calendar", e);
            return "❌ Error fetching holidays";
        }
    }

    /**
     * Adds a goal for an employee.
     */
    public boolean addGoal(int empId, String description, String deadline, String priority, String metrics) {
        String sql = """
                INSERT INTO goals (emp_id, description, deadline, priority, success_metrics)
                VALUES (?, ?, ?, ?, ?)
                """;
        try (Connection con = DBUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, empId);
            ps.setString(2, description);
            ps.setDate(3, java.sql.Date.valueOf(deadline));
            ps.setString(4, priority);
            ps.setString(5, metrics);

            int rows = ps.executeUpdate();
            logger.info("Added goal for empId={}, rows affected={}", empId, rows);
            return rows > 0;
        } catch (SQLException e) {
            logger.error("Error adding goal for empId={}", empId, e);
            return false;
        }
    }

    /**
     * Assigns or updates an employee's manager.
     */
    public boolean assignManager(int empId, int managerId) {
        String sql = "UPDATE employees SET manager_id=? WHERE emp_id=?";

        try (Connection con = DBUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, managerId);
            ps.setInt(2, empId);

            int rows = ps.executeUpdate();
            logger.info("Assigned managerId={} to empId={}, rows affected={}", managerId, empId, rows);
            return rows > 0;

        } catch (SQLException e) {
            logger.error("Error assigning manager for empId={}", empId, e);
            return false;
        }
    }

}
