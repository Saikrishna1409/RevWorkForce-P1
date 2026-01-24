package org.example.dao;

import org.example.util.DBUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class DepartmentDao {

    public boolean addDepartment(String name) {

        String sql = "INSERT INTO departments(name) VALUES(?)";

        try (Connection con = DBUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, name);
            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean addDesignation(int deptId, String designation) {

        String sql = "INSERT INTO designations(dept_id, name) VALUES(?, ?)";

        try (Connection con = DBUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, deptId);
            ps.setString(2, designation);
            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public void viewDepartments() {

        String sql = "SELECT dept_id, name FROM departments";

        try (Connection con = DBUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                System.out.println(
                        rs.getInt("dept_id") + " | " + rs.getString("name")
                );
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
