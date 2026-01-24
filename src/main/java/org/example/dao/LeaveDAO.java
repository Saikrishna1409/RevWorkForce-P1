package org.example.dao;

import org.example.util.DBUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class LeaveDAO {

    // APPLY LEAVE
    public void applyLeave(int empId, String from, String to, String type, String reason) {
        String sql = """
            INSERT INTO leave_requests(emp_id, from_date, to_date, type, reason)
            VALUES(?,?,?,?,?)
            """;

        try (Connection con = DBUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, empId);
            ps.setString(2, from);
            ps.setString(3, to);
            ps.setString(4, type);
            ps.setString(5, reason);

            ps.executeUpdate();
            System.out.println("Leave applied successfully.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // VIEW LEAVES
    public void viewLeaves(int empId) {
        String sql = "SELECT * FROM leave_requests WHERE emp_id=?";

        try (Connection con = DBUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, empId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                System.out.println(
                        rs.getInt("leave_id") + " | " +
                                rs.getString("type") + " | " +
                                rs.getString("status")
                );
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // CANCEL PENDING LEAVE
    public void cancelLeave(int leaveId) {
        String sql = "DELETE FROM leave_requests WHERE leave_id=? AND status='PENDING'";

        try (Connection con = DBUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, leaveId);
            ps.executeUpdate();
            System.out.println("Leave cancelled.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // MANAGER APPROVE / REJECT
    public void updateLeaveStatus(int leaveId, String status, String comment) {
        String sql = """
            UPDATE leave_requests
            SET status=?, manager_comment=?
            WHERE leave_id=?
            """;

        try (Connection con = DBUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, status);
            ps.setString(2, comment);
            ps.setInt(3, leaveId);

            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean addOrUpdateLeaveType(String type, int quota) {

        String sql = """
            INSERT INTO leave_types (type, annual_quota)
            VALUES (?, ?)
            ON DUPLICATE KEY UPDATE annual_quota = ?
            """;

        try (Connection con = DBUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, type);
            ps.setInt(2, quota);
            ps.setInt(3, quota);

            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean adjustLeaveBalance(int empId, String type, int days) {

        String sql = """
            UPDATE leave_balances
            SET balance = balance + ?
            WHERE emp_id = ? AND leave_type = ?
            """;

        try (Connection con = DBUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, days);
            ps.setInt(2, empId);
            ps.setString(3, type);

            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // set holiday calendar
    public boolean addHoliday(String date, String name) {

        String sql = """
            INSERT INTO holidays (holiday_date, holiday_name)
            VALUES (?, ?)
            """;

        try (Connection con = DBUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, date);
            ps.setString(2, name);

            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}

