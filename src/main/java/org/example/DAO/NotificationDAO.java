package org.example.DAO;

import org.example.util.DBUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class NotificationDAO {

    private static final Logger logger = LogManager.getLogger(NotificationDAO.class);

    // SEND NOTIFICATION
    public boolean send(int empId, String msg) {
        String sql = "INSERT INTO notifications(emp_id, message) VALUES (?,?)";

        try (Connection con = DBUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, empId);
            ps.setString(2, msg);

            int rows = ps.executeUpdate();
            logger.info("Notification sent to empId={}, rows affected={}", empId, rows);
            return rows > 0;

        } catch (SQLException e) {
            logger.error("Error sending notification to empId={}", empId, e);
            return false;
        }
    }

    // VIEW UNREAD NOTIFICATIONS
    public String view(int empId) {
        String sql = "SELECT message FROM notifications WHERE emp_id=? AND is_read=0";
        StringBuilder sb = new StringBuilder();
        sb.append("===== Notifications =====\n");

        try (Connection con = DBUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, empId);
            try (ResultSet rs = ps.executeQuery()) {
                boolean hasData = false;
                while (rs.next()) {
                    hasData = true;
                    sb.append("🔔 ").append(rs.getString("message")).append("\n");
                }
                if (!hasData) return null; // Controller can handle null
            }
            return sb.toString();

        } catch (SQLException e) {
            logger.error("Error fetching notifications for empId={}", empId, e);
            return "❌ Error fetching notifications";
        }
    }
}
