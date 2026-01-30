package org.example.controller;

import org.example.model.Employee;
import org.example.service.EmployeeService;

import java.util.Scanner;

public class EmployeeController {

    // Service layer to handle employee-related business logic
    private final EmployeeService employeeService = new EmployeeService();
    // Scanner to read console input
    private final Scanner sc = new Scanner(System.in);

    /**
     * Displays the main employee menu and handles user interaction.
     * Executes actions based on user choice.
     *
     * @param emp currently logged-in employee
     * @throws Exception if any service method throws an exception
     */
    public void menu(Employee emp) throws Exception {
        int ch;

        do {
            // Display the employee menu
            System.out.println("""
            ===== EMPLOYEE MENU =====
            1. View Profile
            2. Apply Leave
            3. View Leaves
            4. Submit Performance Review
            5. View Notifications
            6. View Goals
            7. Update Profile
            8. View Reporting Manager
            9. Leave Status
            10. Cancel Leave
            11. View Holidays
            12. Set Goal
            0. Logout
            """);

            ch = Integer.parseInt(sc.nextLine());

            // Execute action based on menu choice
            switch (ch) {
                case 1 -> employeeService.viewProfile(emp.getId()); // View employee profile
                case 2 -> applyLeave(emp); // Apply for leave
                case 3 -> employeeService.viewLeaves(emp.getId()); // View leave history
                case 4 -> submitReview(emp); // Submit self performance review
                case 5 -> employeeService.viewNotifications(emp.getId()); // View notifications
                case 6 -> employeeService.viewGoals(emp.getId()); // View goals
                case 7 -> updateProfile(emp); // Update profile details
                case 8 -> { // View reporting manager
                    if (!employeeService.viewReportingManager(emp.getId())) {
                        System.out.println("❌ Reporting manager not assigned");
                    }
                }
                case 9 -> { // View applied leaves
                    if (!employeeService.viewAppliedLeaves(emp.getId())) {
                        System.out.println("ℹ️ No leave applications found");
                    }
                }
                case 10 -> cancelLeave(emp); // Cancel leave request
                case 11 -> { // View company holidays
                    if (!employeeService.viewHolidayCalendar()) {
                        System.out.println("ℹ️ No holidays configured");
                    }
                }
                case 12 -> addGoal(emp); // Add new goal
                case 0 -> System.out.println("🔒 Logged out"); // Logout
                default -> System.out.println("❌ Invalid choice"); // Invalid menu option
            }

        } while (ch != 0);
    }

    /**
     * Handles applying leave for the employee.
     */
    private void applyLeave(Employee emp) {
        System.out.print("From Date (YYYY-MM-DD): ");
        String from = sc.nextLine();
        System.out.print("To Date (YYYY-MM-DD): ");
        String to = sc.nextLine();
        System.out.print("Leave Type (CL/SL/PL): ");
        String type = sc.nextLine();
        System.out.print("Reason: ");
        String reason = sc.nextLine();

        employeeService.applyLeave(emp.getId(), from, to, type, reason);
    }

    /**
     * Handles submitting a self-performance review.
     */
    private void submitReview(Employee emp) {
        System.out.print("Deliverables: ");
        String del = sc.nextLine();
        System.out.print("Achievements: ");
        String ach = sc.nextLine();
        System.out.print("Self Rating (1-5): ");
        int rating = Integer.parseInt(sc.nextLine());

        employeeService.submitReview(emp.getId(), del, ach, rating);
    }

    /**
     * Handles updating the employee's profile information.
     */
    private void updateProfile(Employee emp) throws Exception {
        System.out.print("Phone: ");
        String phone = sc.nextLine();
        System.out.print("Address: ");
        String address = sc.nextLine();
        System.out.print("Emergency Contact: ");
        String emergency = sc.nextLine();

        boolean success = employeeService.updateProfile(emp.getId(), phone, address, emergency);
        System.out.println(success ? "✅ Profile updated" : "❌ Update failed");
    }

    /**
     * Handles cancelling an employee leave request.
     */
    private void cancelLeave(Employee emp) {
        System.out.print("Leave ID: ");
        int leaveId = Integer.parseInt(sc.nextLine());

        boolean success = employeeService.cancelLeave(emp.getId(), leaveId);
        System.out.println(success
                ? "✅ Leave cancelled"
                : "❌ Only PENDING leaves can be cancelled");
    }

    /**
     * Handles adding a new goal for the employee.
     */
    private void addGoal(Employee emp) {
        System.out.print("Description: ");
        String desc = sc.nextLine();
        System.out.print("Deadline: ");
        String deadline = sc.nextLine();
        System.out.print("Priority: ");
        String priority = sc.nextLine();
        System.out.print("Success Metrics: ");
        String metrics = sc.nextLine();

        boolean success = employeeService.addGoal(emp.getId(), desc, deadline, priority, metrics);
        System.out.println(success ? "✅ Goal added" : "❌ Failed");
    }
}
