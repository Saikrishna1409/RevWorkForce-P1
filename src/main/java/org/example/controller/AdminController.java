package org.example.controller;

import org.example.service.AdminService;

import java.util.Scanner;

public class AdminController {

    // Service layer instance to handle admin operations
    private final AdminService adminService = new AdminService();
    // Scanner to read console input
    private final Scanner sc = new Scanner(System.in);

    /**
     * Main menu loop for admin actions.
     * Continuously displays options until admin chooses to logout (0).
     */
    public void menu() {
        int choice;

        do {
            // Display admin options
            System.out.println("""
            ===== ADMIN MENU =====
            1. Add New Employee
            2. Update Employee Information
            3. View/Search Employees
            4. Deactivate / Reactivate Employee
            5. Reset Employee Password
            6. Assign / Change Reporting Manager
            7. Configure Leave Types & Quotas
            8. Adjust Leave Balance
            9. Set Company Holiday Calendar
            10. Manage Departments & Designations
            11. Configure Performance Review Cycles
            12. View System Audit Logs
            0. Logout
            ======================
            """);

            System.out.print("Enter choice: ");
            choice = Integer.parseInt(sc.nextLine());

            // Call corresponding methods based on choice
            switch (choice) {
                case 1 -> addEmployee();              // Add new employee
                case 2 -> updateEmployee();           // Update existing employee
                case 3 -> adminService.viewEmployees(sc); // View/search employees
                case 4 -> updateStatus();             // Activate/deactivate employee
                case 5 -> resetPassword();            // Reset employee password
                case 6 -> assignManager();            // Assign or change manager
                case 7 -> configureLeaveType();       // Configure leave types & quotas
                case 8 -> adjustLeave();              // Adjust leave balance for employee
                case 9 -> addHoliday();               // Set company holiday
                case 10 -> manageDepartments();       // Add/manage departments
                case 11 -> configureReviewCycle();    // Add performance review cycle
                case 12 -> adminService.viewAuditLogs(); // View system audit logs
                case 0 -> System.out.println("🔒 Logged out"); // Exit menu
                default -> System.out.println("❌ Invalid choice"); // Invalid input
            }

        } while (choice != 0);
    }

    // ===================== MENU OPTION METHODS =====================

    /** Adds a new employee using console input. */
    private void addEmployee() {
        System.out.print("Emp ID: ");
        int id = Integer.parseInt(sc.nextLine());
        System.out.print("Name: ");
        String name = sc.nextLine();
        System.out.print("Email: ");
        String email = sc.nextLine();
        System.out.print("Designation: ");
        String designation = sc.nextLine();
        System.out.print("Manager ID: ");
        int managerId = Integer.parseInt(sc.nextLine());

        boolean result = adminService.addEmployee(id, name, email, designation, managerId);
        System.out.println(result ? "✅ Employee added" : "❌ Failed");
    }

    /** Updates employee's email and designation. */
    private void updateEmployee() {
        System.out.print("Emp ID: ");
        int id = Integer.parseInt(sc.nextLine());
        System.out.print("Email: ");
        String email = sc.nextLine();
        System.out.print("Designation: ");
        String designation = sc.nextLine();

        boolean result = adminService.updateEmployee(id, email, designation);
        System.out.println(result ? "✅ Updated" : "❌ Failed");
    }

    /** Activates or deactivates an employee account. */
    private void updateStatus() {
        System.out.print("Emp ID: ");
        int id = Integer.parseInt(sc.nextLine());
        System.out.print("Active (true/false): ");
        boolean active = Boolean.parseBoolean(sc.nextLine());

        boolean result = adminService.updateStatus(id, active);
        System.out.println(result ? "✅ Status updated" : "❌ Failed");
    }

    /** Resets an employee password. */
    private void resetPassword() {
        System.out.print("Emp ID: ");
        int id = Integer.parseInt(sc.nextLine());
        System.out.print("New Password: ");
        String pwd = sc.nextLine();

        adminService.resetPassword(id, pwd);
        System.out.println("✅ Password reset");
    }

    /** Assigns or changes the reporting manager for an employee. */
    private void assignManager() {
        System.out.print("Emp ID: ");
        int empId = Integer.parseInt(sc.nextLine());
        System.out.print("Manager ID: ");
        int managerId = Integer.parseInt(sc.nextLine());

        boolean result = adminService.assignManager(empId, managerId);
        System.out.println(result ? "✅ Assigned" : "❌ Failed");
    }

    /** Configures leave types and their annual quotas. */
    private void configureLeaveType() {
        System.out.print("Leave Type: ");
        String type = sc.nextLine();
        System.out.print("Quota: ");
        int quota = Integer.parseInt(sc.nextLine());

        boolean result = adminService.configureLeave(type, quota);
        System.out.println(result ? "✅ Configured" : "❌ Failed");
    }

    /** Adjusts leave balance for an employee. */
    private void adjustLeave() {
        System.out.print("Emp ID: ");
        int id = Integer.parseInt(sc.nextLine());
        System.out.print("Leave Type: ");
        String type = sc.nextLine();
        System.out.print("Days: ");
        int days = Integer.parseInt(sc.nextLine());

        boolean result = adminService.adjustLeave(id, type, days);
        System.out.println(result ? "✅ Adjusted" : "❌ Failed");
    }

    /** Adds a holiday to the company calendar. */
    private void addHoliday() {
        System.out.print("Date: ");
        String date = sc.nextLine();
        System.out.print("Name: ");
        String name = sc.nextLine();

        boolean result = adminService.addHoliday(date, name);
        System.out.println(result ? "✅ Holiday added" : "❌ Failed");
    }

    /** Adds or manages departments in the system. */
    private void manageDepartments() {
        System.out.print("Department Name: ");
        String name = sc.nextLine();
        boolean result = adminService.addDepartment(name);
        System.out.println(result ? "✅ Added" : "❌ Failed");
    }

    /** Adds a performance review cycle. */
    private void configureReviewCycle() {
        System.out.print("Cycle Name: ");
        String name = sc.nextLine();
        System.out.print("Start Date: ");
        String start = sc.nextLine();
        System.out.print("End Date: ");
        String end = sc.nextLine();

        boolean result = adminService.addReviewCycle(name, start, end);
        System.out.println(result ? "✅ Review cycle added" : "❌ Failed");
    }
}
