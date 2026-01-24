package org.example.dao;

import org.example.util.DBUtil;
import java.sql.*;

public class EmployeeDAO {

    public void viewProfile(int empId) {
        String sql = "SELECT * FROM employees WHERE emp_id=?";
        try (Connection con = DBUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, empId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                System.out.println("Name: " + rs.getString("name"));
                System.out.println("Phone: " + rs.getString("phone"));
                System.out.println("Department: " + rs.getString("department"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void updateProfile(
            int empId, String phone, String address, String emergency)
            throws Exception {

        Connection con = DBUtil.getConnection();
        PreparedStatement ps = con.prepareStatement(
                "UPDATE employees SET phone=?, address=?, emergency_contact=? WHERE emp_id=?"
        );

        ps.setString(1, phone);
        ps.setString(2, address);
        ps.setString(3, emergency);
        ps.setInt(4, empId);
        ps.executeUpdate();
        System.out.println("Updated");

//        AuditLogger.log(empId, "UPDATE_PROFILE", "employees", empId);
    }

    public static void viewManager(int empId) throws Exception {
        Connection con = DBUtil.getConnection();
        PreparedStatement ps = con.prepareStatement(
                "SELECT m.emp_id, m.name, m.email " +
                        "FROM employees e JOIN employees m ON e.manager_id=m.emp_id " +
                        "WHERE e.emp_id=?"
        );

        ps.setInt(1, empId);
        ResultSet rs = ps.executeQuery();

        if (rs.next()) {
            System.out.println("Manager ID: " + rs.getInt(1));
            System.out.println("Name: " + rs.getString(2));
            System.out.println("Email: " + rs.getString(3));
        }
    }


    public boolean addEmployee(int empid, String name, String email,
                               String designation, int managerId) {

        String sql = """
            INSERT INTO employees
            (empid,name, email, designation, manager_id)
            VALUES (?, ?, ?, ?, ?)
            """;

        try (Connection con = DBUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, name);
            ps.setString(2, email);
            ps.setString(3, designation);
            ps.setInt(4, managerId);

            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean updateEmployee(int empId, String email, String designation) {
        String sql = "UPDATE employees SET email=?, designation=? WHERE emp_id=?";

        try (Connection con = DBUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, email);
            ps.setString(2, designation);
            ps.setInt(3, empId);

            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public void viewAllEmployees() {
        String sql = "SELECT emp_id, name, email, designation, active FROM employees";

        try (Connection con = DBUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                System.out.printf(
                        "%d | %s | %s | %s | %s%n",
                        rs.getInt("emp_id"),
                        rs.getString("name"),
                        rs.getString("email"),
                        rs.getString("designation"),
                        rs.getBoolean("active") ? "Active" : "Inactive"
                );
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void searchById(int empId) {
        String sql = "SELECT * FROM employees WHERE emp_id=?";

        try (Connection con = DBUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, empId);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                System.out.println(
                        rs.getInt("emp_id") + " | " +
                                rs.getString("name") + " | " +
                                rs.getString("email")
                );
            } else {
                System.out.println("❌ Employee not found");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void searchByName(String name) {
        String sql = "SELECT emp_id, name, email FROM employees WHERE name LIKE ?";

        try (Connection con = DBUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, "%" + name + "%");
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                System.out.println(
                        rs.getInt("emp_id") + " | " +
                                rs.getString("name") + " | " +
                                rs.getString("email")
                );
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean updateStatus(int empId, boolean active) {
        String sql = "UPDATE employees SET active=? WHERE emp_id=?";

        try (Connection con = DBUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setBoolean(1, active);
            ps.setInt(2, empId);
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean assignManager(int empId, int managerId) {

        String sql = """
        UPDATE employees
        SET manager_id = ?
        WHERE emp_id = ?
        """;

        try (Connection con = DBUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, managerId);
            ps.setInt(2, empId);

            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean viewReportingManager(int empId) {

        String sql = """
        SELECT m.emp_id, m.name, m.designation, m.department
        FROM employees e
        JOIN employees m ON e.manager_id = m.emp_id
        WHERE e.emp_id = ?
        """;

        try (Connection con = DBUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, empId);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                System.out.println("""
                ===== Reporting Manager =====
                ID          : %d
                Name        : %s
                Designation : %s
                Department  : %s
                """.formatted(
                        rs.getInt("emp_id"),
                        rs.getString("name"),
                        rs.getString("designation"),
                        rs.getString("department")
                ));
                return true;
            }
            return false;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    public boolean viewAppliedLeaves(int empId) {

        String sql = """
        SELECT leave_id, type, from_date, to_date, status, manager_comment
        FROM leave_requests
        WHERE emp_id = ?
        ORDER BY leave_id DESC
        """;

        try (Connection con = DBUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, empId);
            ResultSet rs = ps.executeQuery();

            boolean hasData = false;

            System.out.println("""
        ===== Your Leave Applications =====
        ID | Type | From | To | Status | Manager Comments
        --------------------------------------------------
        """);

            while (rs.next()) {
                hasData = true;
                System.out.printf(
                        "%d | %s | %s | %s | %s | %s%n",
                        rs.getInt("leave_id"),
                        rs.getString("type"),
                        rs.getDate("from_date"),
                        rs.getDate("to_date"),
                        rs.getString("status"),
                        rs.getString("manager_comment") == null
                                ? "-"
                                : rs.getString("manager_comment")
                );
            }

            return hasData;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    public boolean cancelPendingLeave(int empId, int leaveId) {

        String sql = """
        DELETE FROM leave_requests
        WHERE leave_id = ?
          AND emp_id = ?
          AND status = 'PENDING'
        """;

        try (Connection con = DBUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, leaveId);
            ps.setInt(2, empId);

            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean viewHolidayCalendar() {

        String sql = """
        SELECT holiday_date, holiday_name
        FROM holidays
        ORDER BY holiday_date
        """;

        try (Connection con = DBUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            boolean hasData = false;

            System.out.println("""
        ===== Company Holiday Calendar =====
        Date       | Holiday
        ------------------------------
        """);

            while (rs.next()) {
                hasData = true;
                System.out.printf(
                        "%s | %s%n",
                        rs.getDate("holiday_date"),
                        rs.getString("holiday_name")
                );
            }

            return hasData;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean addGoal(int empId,
                           String description,
                           String deadline,
                           String priority,
                           String metrics) {

        String sql = """
        INSERT INTO goals
        (emp_id, description, deadline, priority, success_metrics)
        VALUES (?, ?, ?, ?, ?)
        """;

        try (Connection con = DBUtil.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, empId);
            ps.setString(2, description);
            ps.setDate(3, java.sql.Date.valueOf(deadline));
            ps.setString(4, priority);
            ps.setString(5, metrics);

            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }




}
