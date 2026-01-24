package org.example.dao;

import org.example.util.DBUtil;
import org.example.model.Goal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class GoalDAO {

    public  void addGoal(Goal g) throws Exception {
        Connection con = DBUtil.getConnection();
        PreparedStatement ps = con.prepareStatement(
                "INSERT INTO goals(emp_id,description,deadline,priority,success_metrics,progress) " +
                        "VALUES (?,?,?,?,?,0)"
        );

        ps.setInt(1, g.getEmpId());
        ps.setString(2, g.getDescription());
        ps.setDate(3, java.sql.Date.valueOf(g.getDeadline()));
        ps.setString(4, g.getPriority());
        ps.setString(5, g.getSuccessMetrics());

        ps.executeUpdate();
    }

    public void viewGoals(int empId) throws Exception {
        Connection con = DBUtil.getConnection();
        PreparedStatement ps = con.prepareStatement(
                "SELECT description, progress FROM goals WHERE emp_id=?"
        );

        ps.setInt(1, empId);
        ResultSet rs = ps.executeQuery();

        while (rs.next()) {
            System.out.println(rs.getString(1) + " | Progress: " + rs.getInt(2) + "%");
        }
    }
}
