package org.example.DAO;

import org.example.model.Employee;
import org.example.util.DBUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * DAO responsible for authentication-related database operations.
 * Handles login, password validation, and password updates.
 */
public class AuthDAO {

    // Logger for authentication-related events and errors
    private static final Logger logger =
            LogManager.getLogger(AuthDAO.class);

    /**
     * Finds an active employee matching the given credentials.
     * Used during login.
     *
     * @param empId employee ID
     * @param password plain-text password (will be hashed later)
     * @return Employee object if valid and active, otherwise null
     */
    public Employee findActiveEmployee(int empId, String password) throws SQLException {

        String sql = """
            SELECT emp_id, name, role, manager_id
            FROM employees
            WHERE emp_id = ?
            AND password = ?
            AND active = 1
            """;

        try (Connection con = DBUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, empId);
            ps.setString(2, password);

            ResultSet rs = ps.executeQuery();

            // If employee exists and credentials match, return Employee object
            if (rs.next()) {
                return new Employee(
                        rs.getInt("emp_id"),
                        rs.getString("name"),
                        rs.getString("role"),
                        rs.getInt("manager_id")
                );
            }

        } catch (Exception e) {
            // Log database-level authentication failure
            logger.error("DB error during login for emp-id={}", empId, e);
            throw e;
        }

        // No matching active employee found
        return null;
    }

    /**
     * Updates password for the given employee ID.
     * Used for admin reset and password change.
     */
    public boolean updatePassword(int empId, String newPassword) {

        String sql = """
            UPDATE employees
            SET password = ?
            WHERE emp_id = ?
            """;

        try (Connection con = DBUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, newPassword);
            ps.setInt(2, empId);

            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace(); // Can be replaced with logger later
            return false;
        }
    }

    /**
     * Validates whether the provided old password matches the current password.
     * Used during password change.
     */
    public boolean validateOldPassword(int empId, String oldPassword) {

        String sql = """
            SELECT 1
            FROM employees
            WHERE emp_id = ?
            AND password = ?
            AND active = 1
            """;

        try (Connection con = DBUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, empId);
            ps.setString(2, oldPassword);

            // Returns true if a matching row exists
            return ps.executeQuery().next();

        } catch (Exception e) {
            return false;
        }
    }
}
