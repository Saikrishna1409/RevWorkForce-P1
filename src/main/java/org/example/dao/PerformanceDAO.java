package org.example.dao;

import org.example.util.DBUtil;
import java.sql.*;

public class PerformanceDAO {

    public void submitReview(int empId, String del, String acc, int rating) {
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
            ps.executeUpdate();
            System.out.println("✅ Review submitted");
        } catch (Exception e) { e.printStackTrace(); }
    }

    public void provideFeedback(int empId, String feedback, int rating) {
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
            ps.executeUpdate();
        } catch (Exception e) { e.printStackTrace(); }
    }

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

            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
