package org.example.DAO;

import org.example.util.DBUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

// DAO class responsible for manager-related database operations
// Handles team management, approvals, reviews, and reporting
public class ManagerDAO {

    // Fetches and displays all direct reportees of a manager
    public void viewReportees(int managerId) {
        String sql = "SELECT emp_id, name FROM employees WHERE manager_id=?";
        executeAndPrint(sql, managerId);
    }

    // Fetches and displays all leave requests raised by team members
    public void viewTeamLeaveRequests(int managerId) {
        String sql = """
            SELECT lr.id, e.name, lr.leave_type, lr.status
            FROM leave_request lr
            JOIN employees e ON lr.emp_id = e.emp_id
            WHERE e.manager_id=?
            """;
        executeAndPrint(sql, managerId);
    }

    // Updates the status and comments for a leave request (Approve/Reject)
    public boolean updateLeaveStatus(int leaveId, String status, String comments) {
        String sql = "UPDATE leave_request SET status=?, manager_comments=? WHERE id=?";
        return executeUpdate(sql, status, comments, leaveId);
    }

    // Displays leave balance summary for all employees under a manager
    public void viewTeamLeaveSummary(int managerId) {
        String sql = """
            SELECT e.name, lb.leave_type, lb.balance
            FROM leave_balances lb
            JOIN employees e ON lb.emp_id = e.emp_id
            WHERE e.manager_id=?
            """;
        executeAndPrint(sql, managerId);
    }

    // Displays uploaded performance documents of team members
    public void viewPerformanceDocs(int managerId) {
        String sql = """
            SELECT e.name, p.document_name
            FROM performance_docs p
            JOIN employees e ON p.emp_id = e.emp_id
            WHERE e.manager_id=?
            """;
        executeAndPrint(sql, managerId);
    }

    // Inserts feedback provided by manager for an employee
    public boolean addFeedback(int empId, String feedback) {
        String sql = "INSERT INTO performance_feedback(emp_id, feedback) VALUES(?,?)";
        return executeUpdate(sql, empId, feedback);
    }

    // Updates performance rating of an employee
    public boolean rateEmployee(int empId, int rating) {
        String sql = "UPDATE performance_review SET rating=? WHERE emp_id=?";
        return executeUpdate(sql, rating, empId);
    }

    // Displays goals and their status for employees under the manager
    public void viewEmployeeGoals(int managerId) {
        String sql = """
            SELECT e.name, g.goal, g.status
            FROM goal g
            JOIN employees e ON g.emp_id = e.emp_id
            WHERE e.manager_id=?
            """;
        executeAndPrint(sql, managerId);
    }

    // Tracks goal status (currently reuses employee goal view)
    public void trackGoalStatus(int managerId) {
        viewEmployeeGoals(managerId);
    }

    // Displays team hierarchy (currently same as viewing reportees)
    public void viewTeamHierarchy(int managerId) {
        viewReportees(managerId);
    }

    // Displays attendance summary (number of days present) for team members
    public void viewAttendanceSummary(int managerId) {
        String sql = """
            SELECT e.name, COUNT(a.date) AS days_present
            FROM attendance a
            JOIN employees e ON a.emp_id = e.emp_id
            WHERE e.manager_id=?
            GROUP BY e.name
            """;
        executeAndPrint(sql, managerId);
    }

    // Generates a simple performance report based on employee ratings
    public void generatePerformanceReport(int managerId) {
        String sql = """
            SELECT e.name, pr.rating
            FROM performance_review pr
            JOIN employees e ON pr.emp_id = e.emp_id
            WHERE e.manager_id=?
            """;
        executeAndPrint(sql, managerId);
    }

    // Displays notifications addressed to the manager
    public void viewNotifications(int managerId) {
        String sql = "SELECT user_id,message FROM notification WHERE user_id=?";
        executeAndPrint(sql, managerId);
    }

    /* ---------- Helper Methods ---------- */

    // Executes SELECT queries with a single integer parameter
    // Prints the first two columns of each result row
    private void executeAndPrint(String sql, int id) {
        try (Connection con = DBUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            // Sets the parameter (usually managerId)
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            // Prints query result row by row
            while (rs.next()) {
                System.out.println(rs.getString(1) + " | " + rs.getString(2));
            }

        } catch (Exception e) {
            // Handles database or execution errors
        }
    }

    // Executes INSERT / UPDATE / DELETE queries
    // Accepts dynamic parameters using varargs
    private boolean executeUpdate(String sql, Object... params) {
        try (Connection con = DBUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            // Dynamically sets prepared statement parameters
            for (int i = 0; i < params.length; i++) {
                ps.setObject(i + 1, params[i]);
            }

            // Returns true if at least one row is affected
            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            // Handles SQL execution errors

            return false;
        }
    }
}

