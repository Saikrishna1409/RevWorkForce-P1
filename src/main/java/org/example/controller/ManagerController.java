package org.example.controller;

import org.example.model.Employee;
import org.example.service.ManagerService;

import java.util.Scanner;

/**
 * Controller class for Manager operations.
 * Handles menu display and user interaction for managers.
 */
public class ManagerController {

    // Service layer to handle manager-related business logic
    private final ManagerService managerService = new ManagerService();
    // Scanner for reading console input
    private final Scanner sc = new Scanner(System.in);

    /**
     * Displays the main manager menu and executes actions
     * based on the manager's input.
     *
     * @param manager currently logged-in manager
     */
    public void menu(Employee manager) {
        int choice;

        do {
            // Display manager menu
            System.out.println("""
            ===== MANAGER MENU =====
            1. View Direct Reportees
            2. View Team Leave Applications
            3. Approve / Reject Leave
            4. View Team Leave Summary
            5. View Performance Documents
            6. Provide Feedback
            7. Rate Employee
            8. View Employee Goals
            9. Track Goal Status
            10. View Team Hierarchy
            11. View Attendance Summary
            12. Generate Performance Report
            13. View Notifications
            0. Logout
            """);

            choice = Integer.parseInt(sc.nextLine());

            // Execute action based on menu choice
            switch (choice) {
                case 1 -> managerService.viewReportees(manager.getId()); // View direct reportees
                case 2 -> managerService.viewTeamLeaveRequests(manager.getId()); // View team leave requests
                case 3 -> approveRejectLeave(); // Approve or reject a leave
                case 4 -> managerService.viewTeamLeaveSummary(manager.getId()); // Team leave summary
                case 5 -> managerService.viewPerformanceDocs(manager.getId()); // View performance documents
                case 6 -> provideFeedback(); // Submit feedback for an employee
                case 7 -> rateEmployee(); // Rate an employee
                case 8 -> managerService.viewEmployeeGoals(manager.getId()); // View goals of reportees
                case 9 -> managerService.trackGoalStatus(manager.getId()); // Track goal progress
                case 10 -> managerService.viewTeamHierarchy(manager.getId()); // View team hierarchy
                case 11 -> managerService.viewAttendanceSummary(manager.getId()); // Attendance summary
                case 12 -> managerService.generatePerformanceReport(manager.getId()); // Generate report
                case 13 -> managerService.viewNotifications(manager.getId()); // View notifications
                case 0 -> System.out.println("🔒 Logged out"); // Logout
                default -> System.out.println("❌ Invalid option"); // Invalid menu input
            }

        } while (choice != 0);
    }

    /**
     * Handles approving or rejecting a leave request.
     */
    private void approveRejectLeave() {
        System.out.print("Leave ID: ");
        int leaveId = Integer.parseInt(sc.nextLine());

        System.out.print("Status (APPROVED/REJECTED): ");
        String status = sc.nextLine();

        System.out.print("Comments: ");
        String comments = sc.nextLine();

        boolean success = managerService.updateLeaveStatus(leaveId, status, comments);
        System.out.println(success ? "✅ Updated" : "❌ Failed");
    }

    /**
     * Handles providing feedback to a specific employee.
     */
    private void provideFeedback() {
        System.out.print("Employee ID: ");
        int empId = Integer.parseInt(sc.nextLine());

        System.out.print("Feedback: ");
        String feedback = sc.nextLine();

        boolean success = managerService.addFeedback(empId, feedback);
        System.out.println(success ? "✅ Feedback submitted" : "❌ Failed");
    }

    /**
     * Handles rating an employee.
     */
    private void rateEmployee() {
        System.out.print("Employee ID: ");
        int empId = Integer.parseInt(sc.nextLine());

        System.out.print("Rating (1–5): ");
        int rating = Integer.parseInt(sc.nextLine());

        boolean success = managerService.rateEmployee(empId, rating);
        System.out.println(success ? "✅ Rating saved" : "❌ Failed");
    }
}
