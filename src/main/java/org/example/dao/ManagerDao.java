package org.example.dao;

import org.example.util.DBUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class ManagerDao {

    public void viewReportees(int managerId) {
        String sql = "SELECT emp_id, name FROM employees WHERE manager_id=?";
        executeAndPrint(sql, managerId);
    }

    public void viewTeamLeaveRequests(int managerId) {
        String sql = """
            SELECT lr.id, e.name, lr.leave_type, lr.status
            FROM leave_request lr
            JOIN employees e ON lr.emp_id = e.emp_id
            WHERE e.manager_id=?
            """;
        executeAndPrint(sql, managerId);
    }

    public boolean updateLeaveStatus(int leaveId, String status, String comments) {
        String sql = "UPDATE leave_request SET status=?, manager_comments=? WHERE id=?";
        return executeUpdate(sql, status, comments, leaveId);
    }

    public void viewTeamLeaveSummary(int managerId) {
        String sql = """
            SELECT e.name, lb.leave_type, lb.balance
            FROM leave_balances lb
            JOIN employees e ON lb.emp_id = e.emp_id
            WHERE e.manager_id=?
            """;
        executeAndPrint(sql, managerId);
    }

    public void viewPerformanceDocs(int managerId) {
        String sql = """
            SELECT e.name, p.document_name
            FROM performance_docs p
            JOIN employees e ON p.emp_id = e.emp_id
            WHERE e.manager_id=?
            """;
        executeAndPrint(sql, managerId);
    }

    public boolean addFeedback(int empId, String feedback) {
        String sql = "INSERT INTO performance_feedback(emp_id, feedback) VALUES(?,?)";
        return executeUpdate(sql, empId, feedback);
    }

    public boolean rateEmployee(int empId, int rating) {
        String sql = "UPDATE performance_review SET rating=? WHERE emp_id=?";
        return executeUpdate(sql, rating, empId);
    }

    public void viewEmployeeGoals(int managerId) {
        String sql = """
            SELECT e.name, g.goal, g.status
            FROM goal g
            JOIN employees e ON g.emp_id = e.emp_id
            WHERE e.manager_id=?
            """;
        executeAndPrint(sql, managerId);
    }

    public void trackGoalStatus(int managerId) {
        viewEmployeeGoals(managerId);
    }

    public void viewTeamHierarchy(int managerId) {
        viewReportees(managerId);
    }

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

    public void generatePerformanceReport(int managerId) {
        String sql = """
            SELECT e.name, pr.rating
            FROM performance_review pr
            JOIN employees e ON pr.emp_id = e.emp_id
            WHERE e.manager_id=?
            """;
        executeAndPrint(sql, managerId);
    }

    public void viewNotifications(int managerId) {
        String sql = "SELECT user_id,message FROM notification WHERE user_id=?";
        executeAndPrint(sql, managerId);
    }

    /* ---------- Helper Methods ---------- */

    private void executeAndPrint(String sql, int id) {
        try (Connection con = DBUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                System.out.println(rs.getString(1) + " | " + rs.getString(2));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private boolean executeUpdate(String sql, Object... params) {
        try (Connection con = DBUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            for (int i = 0; i < params.length; i++) {
                ps.setObject(i + 1, params[i]);
            }
            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
