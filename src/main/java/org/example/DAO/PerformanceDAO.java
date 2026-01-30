package org.example.DAO;

import org.example.util.DBUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class PerformanceDAO {

    private static final Logger logger = LogManager.getLogger(PerformanceDAO.class);

    // Submit employee self-review
    public String submitReview(int empId, String del, String acc, int rating) {
        String sql = """
            INSERT INTO performance_reviews(emp_id, deliverables, accomplishments, self_rating)
            VALUES (?,?,?,?)
            """;
        try (Connection con = DBUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, empId);
            ps.setString(2, del);
            ps.setString(3, acc);
            ps.setInt(4, rating);

            int rows = ps.executeUpdate();
            logger.info("Performance review submitted for empId={}, rows affected={}", empId, rows);
            return rows > 0 ? "✅ Review submitted successfully." : "❌ Failed to submit review";

        } catch (SQLException e) {
            logger.error("Error submitting review for empId={}", empId, e);
            return "❌ Error submitting review";
        }
    }

    // Manager provides feedback
    public boolean provideFeedback(int empId, String feedback, int rating) {
        String sql = """
            UPDATE performance_reviews
            SET manager_feedback=?, manager_rating=?
            WHERE emp_id=?
            """;
        try (Connection con = DBUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, feedback);
            ps.setInt(2, rating);
            ps.setInt(3, empId);

            int rows = ps.executeUpdate();
            logger.info("Manager feedback updated for empId={}, rows affected={}", empId, rows);
            return rows > 0;

        } catch (SQLException e) {
            logger.error("Error updating manager feedback for empId={}", empId, e);
            return false;
        }
    }

    // Add performance review cycle
    public boolean addReviewCycle(String name, String start, String end) {
        String sql = """
            INSERT INTO performance_cycles (cycle_name, start_date, end_date)
            VALUES (?, ?, ?)
            """;
        try (Connection con = DBUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, name);
            ps.setString(2, start);
            ps.setString(3, end);

            int rows = ps.executeUpdate();
            logger.info("Performance cycle added: name={}, rows affected={}", name, rows);
            return rows > 0;

        } catch (SQLException e) {
            logger.error("Error adding performance cycle: name={}", name, e);
            return false;
        }
    }
}
