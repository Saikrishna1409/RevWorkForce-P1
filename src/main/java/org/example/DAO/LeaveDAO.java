package org.example.DAO;

import org.example.util.DBUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

// DAO class responsible for handling all leave-related database operations
public class LeaveDAO {

    // Logger for tracking leave operations and errors
    private static final Logger logger = LogManager.getLogger(LeaveDAO.class);

    // Applies a new leave request for an employee
    // Returns a success or failure message based on database operation
    public String applyLeave(int empId, String from, String to, String type, String reason) {

        // SQL query to insert a leave request
        String sql = """
            INSERT INTO leave_requests(emp_id, from_date, to_date, type, reason)
            VALUES(?,?,?,?,?)
            """;

        // Executes leave insertion using prepared statement
        try (Connection con = DBUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            // Sets leave request parameters
            ps.setInt(1, empId);
            ps.setString(2, from);
            ps.setString(3, to);
            ps.setString(4, type);
            ps.setString(5, reason);

            // Executes insert and returns result message
            int rows = ps.executeUpdate();
            logger.info("Leave applied for empId={}, rows affected={}", empId, rows);
            return rows > 0 ? "✅ Leave applied successfully." : "❌ Failed to apply leave.";

        } catch (SQLException e) {
            // Logs and handles SQL exceptions during leave application
            logger.error("Error applying leave for empId={}", empId, e);
            return "❌ Error applying leave";
        }
    }

    // Fetches all leave requests of a specific employee
    // Returns formatted leave details or null if no records exist
    public String viewLeaves(int empId) {

        // SQL query to fetch leave details
        String sql = "SELECT leave_id, type, status FROM leave_requests WHERE emp_id=?";
        StringBuilder sb = new StringBuilder();

        // Builds header for leave display
        sb.append("===== Your Leaves =====\nID | Type | Status\n-------------------\n");

        // Executes leave fetch operation
        try (Connection con = DBUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            // Sets employee ID parameter
            ps.setInt(1, empId);

            try (ResultSet rs = ps.executeQuery()) {
                boolean hasData = false;

                // Iterates through leave records
                while (rs.next()) {
                    hasData = true;
                    sb.append(String.format("%d | %s | %s%n",
                            rs.getInt("leave_id"),
                            rs.getString("type"),
                            rs.getString("status")
                    ));
                }

                // Returns null if no leave records are found
                if (!hasData) return null;
            }

            return sb.toString();

        } catch (SQLException e) {
            // Logs SQL error during leave retrieval
            logger.error("Error fetching leaves for empId={}", empId, e);
            return "❌ Error fetching leaves";
        }
    }

    // Cancels a pending leave request for an employee
    // Returns true if the leave is successfully cancelled
    public boolean cancelLeave(int leaveId, int empId) {

        // SQL query to delete only PENDING leave requests
        String sql = "DELETE FROM leave_requests WHERE leave_id=? AND emp_id=? AND status='PENDING'";

        // Executes leave cancellation
        try (Connection con = DBUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            // Sets leave ID and employee ID
            ps.setInt(1, leaveId);
            ps.setInt(2, empId);

            int rows = ps.executeUpdate();
            logger.info("Cancelled leave leaveId={}, empId={}, rows affected={}", leaveId, empId, rows);
            return rows > 0;

        } catch (SQLException e) {
            // Logs SQL error during leave cancellation
            logger.error("Error cancelling leave leaveId={}, empId={}", leaveId, empId, e);
            return false;
        }
    }

    // Updates the status of a leave request (APPROVED / REJECTED)
    // Typically used by managers
    public boolean updateLeaveStatus(int leaveId, String status, String comment) {

        // SQL query to update leave status and manager comment
        String sql = """
            UPDATE leave_requests
            SET status=?, manager_comment=?
            WHERE leave_id=?
            """;

        // Executes leave status update
        try (Connection con = DBUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            // Sets update parameters
            ps.setString(1, status);
            ps.setString(2, comment);
            ps.setInt(3, leaveId);

            int rows = ps.executeUpdate();
            logger.info("Updated leave status leaveId={}, status={}, rows affected={}", leaveId, status, rows);
            return rows > 0;

        } catch (SQLException e) {
            // Logs SQL error during status update
            logger.error("Error updating leave status leaveId={}, status={}", leaveId, status, e);
            return false;
        }
    }

    // Adds a new leave type or updates its annual quota
    // Uses UPSERT logic based on leave type
    public boolean addOrUpdateLeaveType(String type, int quota) {

        // SQL query to insert or update leave quota
        String sql = """
            INSERT INTO leave_types (type, annual_quota)
            VALUES (?, ?)
            ON DUPLICATE KEY UPDATE annual_quota = ?
            """;

        // Executes leave type upsert
        try (Connection con = DBUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            // Sets leave type and quota
            ps.setString(1, type);
            ps.setInt(2, quota);
            ps.setInt(3, quota);

            int rows = ps.executeUpdate();
            logger.info("Leave type added/updated type={}, rows affected={}", type, rows);
            return rows > 0;

        } catch (SQLException e) {
            // Logs SQL error during leave type update
            logger.error("Error adding/updating leave type type={}", type, e);
            return false;
        }
    }

    // Adjusts leave balance for an employee
    // Can be used for crediting or debiting leave days
    public boolean adjustLeaveBalance(int empId, String type, int days) {

        // SQL query to update leave balance
        String sql = """
            UPDATE leave_balances
            SET balance = balance + ?
            WHERE emp_id = ? AND leave_type = ?
            """;

        // Executes leave balance adjustment
        try (Connection con = DBUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            // Sets adjustment parameters
            ps.setInt(1, days);
            ps.setInt(2, empId);
            ps.setString(3, type);

            int rows = ps.executeUpdate();
            logger.info("Adjusted leave balance empId={}, type={}, days={}, rows affected={}", empId, type, days, rows);
            return rows > 0;

        } catch (SQLException e) {
            // Logs SQL error during balance adjustment
            logger.error("Error adjusting leave balance empId={}, type={}", empId, type, e);
            return false;
        }
    }

    // Adds a holiday entry to the company holiday calendar
    public boolean addHoliday(String date, String name) {

        // SQL query to insert holiday record
        String sql = "INSERT INTO holidays (holiday_date, holiday_name) VALUES (?, ?)";

        // Executes holiday insertion
        try (Connection con = DBUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            // Sets holiday date and name
            ps.setString(1, date);
            ps.setString(2, name);

            int rows = ps.executeUpdate();
            logger.info("Added holiday date={}, name={}, rows affected={}", date, name, rows);
            return rows > 0;

        } catch (SQLException e) {
            // Logs SQL error during holiday insertion
            logger.error("Error adding holiday date={}, name={}", date, name, e);
            return false;
        }
    }
}
