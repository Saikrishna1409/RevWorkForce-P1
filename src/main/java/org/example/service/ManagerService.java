package org.example.service;

import org.example.dao.EmployeeDAO;
import org.example.dao.ManagerDao;
import org.example.model.Employee;

import java.util.Scanner;

public class ManagerService {
    private final ManagerDao managerDao = new ManagerDao();
    private final Scanner sc = new Scanner(System.in);

    private Employee employee;
    public void menu(Employee employee) {

        int managerId = employee.getId();
        System.out.println("""
        ===== MANAGER MENU =====
        1. View Direct Reportees
        2. View Team Leave Applications
        3. Approve / Reject Leave Request
        4. View Team Leave Calendar & Balances
        5. Review Performance Documents
        6. Provide Performance Feedback
        7. Rate Employee Performance
        8. Review Employee Goals
        9. Track Team Goal Status
        10. View Team Hierarchy
        11. View Team Attendance Summary
        12. Generate Team Performance Report
        13. View Notifications
        """);

        int choice = Integer.parseInt(sc.nextLine());

        switch (choice) {
            case 1 -> managerDao.viewReportees(managerId);
            case 2 -> managerDao.viewTeamLeaveRequests(managerId);
            case 3 -> approveRejectLeave();
            case 4 -> managerDao.viewTeamLeaveSummary(managerId);
            case 5 -> managerDao.viewPerformanceDocs(managerId);
            case 6 -> provideFeedback();
            case 7 -> ratePerformance();
            case 8 -> managerDao.viewEmployeeGoals(managerId);
            case 9 -> managerDao.trackGoalStatus(managerId);
            case 10 -> managerDao.viewTeamHierarchy(managerId);
            case 11 -> managerDao.viewAttendanceSummary(managerId);
            case 12 -> managerDao.generatePerformanceReport(managerId);
            case 13 -> managerDao.viewNotifications(managerId);
            default -> System.out.println("❌ Invalid option");
        }
    }

    private void approveRejectLeave() {
        System.out.print("Leave Request ID: ");
        int leaveId = Integer.parseInt(sc.nextLine());

        System.out.print("Approve / Reject: ");
        String status = sc.nextLine().toUpperCase();

        System.out.print("Comments: ");
        String comments = sc.nextLine();

        boolean success = managerDao.updateLeaveStatus(leaveId, status, comments);
        System.out.println(success ? "✅ Action completed" : "❌ Failed");
    }

    private void provideFeedback() {
        System.out.print("Employee ID: ");
        int empId = Integer.parseInt(sc.nextLine());

        System.out.print("Feedback: ");
        String feedback = sc.nextLine();

        boolean success = managerDao.addFeedback(empId, feedback);
        System.out.println(success ? "✅ Feedback submitted" : "❌ Failed");
    }

    private void ratePerformance() {
        System.out.print("Employee ID: ");
        int empId = Integer.parseInt(sc.nextLine());

        System.out.print("Rating (1–5): ");
        int rating = Integer.parseInt(sc.nextLine());

        boolean success = managerDao.rateEmployee(empId, rating);
        System.out.println(success ? "✅ Rating submitted" : "❌ Failed");
    }
}

