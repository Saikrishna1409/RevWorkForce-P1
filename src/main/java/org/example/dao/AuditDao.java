package org.example.dao;

import org.example.util.DBUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class AuditDao {

    public void viewLogs() {

        String sql = """
            SELECT action, performed_by, created_at
            FROM audit_logs
            ORDER BY created_at DESC
            """;

        try (Connection con = DBUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                System.out.println(
                        rs.getTimestamp("created_at") + " | " +
                                rs.getString("action") + " | User: " +
                                rs.getInt("performed_by")
                );
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
