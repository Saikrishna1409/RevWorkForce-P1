package org.example.dao;

import org.example.util.DBUtil;
import java.sql.*;

public class NotificationDAO {

    public void send(int empId, String msg) {
        String sql = "INSERT INTO notifications(emp_id, message) VALUES (?,?)";
        try (Connection con = DBUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, empId);
            ps.setString(2, msg);
            ps.executeUpdate();
        } catch (Exception e) { e.printStackTrace(); }
    }

    public void view(int empId) {
        String sql = "SELECT message FROM notifications WHERE emp_id=? AND is_read=0";
        try (Connection con = DBUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, empId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                System.out.println("🔔 " + rs.getString("message"));
            }
        } catch (Exception e) { e.printStackTrace(); }
    }
}
