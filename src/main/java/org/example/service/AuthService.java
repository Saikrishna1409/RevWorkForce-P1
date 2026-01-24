package org.example.service;

import org.example.model.Employee;
import org.example.util.DBUtil;
import java.sql.*;

public class AuthService {

    public Employee login(int empId, String pwd) {
        String sql = "SELECT * FROM employees WHERE emp_id=? AND password=? AND active=1";
        try (Connection con = DBUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, empId);
            ps.setString(2, pwd);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new Employee(
                        rs.getInt("emp_id"),
                        rs.getString("name"),
                        rs.getString("role"),
                        rs.getInt("manager_id")
                );
            }
        } catch (Exception e) { e.printStackTrace(); }
        return null;
    }

    public boolean changePassword(int empId, String oldPwd, String newPwd) {
        String sql = "UPDATE employees SET password=? WHERE emp_id=? AND password=?";
        try (Connection con = DBUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, newPwd);
            ps.setInt(2, empId);
            ps.setString(3, oldPwd);
            return ps.executeUpdate() > 0;
        } catch (Exception e) { return false; }
    }

    public boolean resetPassword(int empId, String newPwd) {
        String sql = "UPDATE employees SET password=? WHERE emp_id=?";
        try (Connection con = DBUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, newPwd);
            ps.setInt(2, empId);
            return ps.executeUpdate() > 0;
        } catch (Exception e) { return false; }
    }
}
