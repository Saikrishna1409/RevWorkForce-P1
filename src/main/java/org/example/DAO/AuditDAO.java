package org.example.DAO;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.model.AuditLog;
import org.example.util.DBUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 * DAO responsible for fetching audit log records from the database.
 * Audit logs track important system actions for monitoring and compliance.
 */
public class AuditDAO {

    // Logger for auditing DAO-level operations and errors
    private static final Logger logger =
            LogManager.getLogger(AuditDAO.class);

    /**
     * Retrieves all audit logs from the database.
     * Logs are ordered by most recent action first.
     *
     * @return List of AuditLog objects
     */
    public List<AuditLog> fetchLogs() {

        // Collection to store fetched audit logs
        List<AuditLog> logs = new ArrayList<>();

        // SQL query to fetch audit details
        String sql = """
            SELECT action, performed_by, created_at
            FROM audit_logs
            ORDER BY created_at DESC
        """;

        // Try-with-resources ensures DB resources are closed automatically
        try (Connection con = DBUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            // Iterate over result set and map each row to AuditLog model
            while (rs.next()) {
                logs.add(new AuditLog(
                        rs.getString("action"),        // Action performed
                        rs.getInt("performed_by"),     // User who performed it
                        rs.getTimestamp("created_at")  // Timestamp of action
                ));
            }

        } catch (Exception e) {
            // Log error and rethrow as unchecked exception
            logger.error("Error fetching audit logs", e);
            throw new RuntimeException(e);
        }

        // Return all fetched audit logs
        return logs;
    }
}
