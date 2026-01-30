package org.example.DAO;

import org.example.util.DBUtil;
import java.sql.Connection;
import java.sql.PreparedStatement;

// DAO class responsible for handling database operations related to departments
public class DepartmentDAO {

    // Adds a new department record into the departments table
    // Returns true if the department is inserted successfully, false otherwise
    public boolean addDepartment(String name) {

        // SQL query to insert a new department
        String sql = "INSERT INTO departments(name) VALUES(?)";

        // Establishes database connection and executes the insert operation
        try (Connection con = DBUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            // Sets the department name parameter
            ps.setString(1, name);

            // Executes the update and returns result status
            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            // Handles any database or SQL-related exceptions
            return false;
        }
    }
}
