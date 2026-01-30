package org.example.DAO;

import org.example.util.DBUtil;
import org.example.model.Goal;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class GoalDAO {

    private static final Logger logger = LogManager.getLogger(GoalDAO.class);

    // ADD GOAL
    public boolean addGoal(Goal g) {
        String sql = """
                INSERT INTO goals(emp_id, description, deadline, priority, success_metrics, progress)
                VALUES (?, ?, ?, ?, ?, 0)
                """;

        try (Connection con = DBUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, g.getEmpId());
            ps.setString(2, g.getDescription());
            ps.setDate(3, java.sql.Date.valueOf(g.getDeadline()));
            ps.setString(4, g.getPriority());
            ps.setString(5, g.getSuccessMetrics());

            int rows = ps.executeUpdate();
            logger.info("Added goal for empId={}, rows affected={}", g.getEmpId(), rows);
            return rows > 0;

        } catch (SQLException e) {
            logger.error("Error adding goal for empId={}", g.getEmpId(), e);
            return false;
        }
    }

    // VIEW GOALS
    public String viewGoals(int empId) {
        String sql = "SELECT description, progress FROM goals WHERE emp_id=?";
        StringBuilder sb = new StringBuilder();
        sb.append("===== Your Goals =====\nDescription | Progress\n------------------------\n");

        try (Connection con = DBUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, empId);
            try (ResultSet rs = ps.executeQuery()) {
                boolean hasData = false;
                while (rs.next()) {
                    hasData = true;
                    sb.append(String.format("%s | Progress: %d%%%n",
                            rs.getString("description"),
                            rs.getInt("progress")
                    ));
                }
                if (!hasData) return null; // Controller handles null
            }
            return sb.toString();

        } catch (SQLException e) {
            logger.error("Error fetching goals for empId={}", empId, e);
            return "❌ Error fetching goals";
        }
    }
}
