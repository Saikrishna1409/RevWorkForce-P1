package org.example.service;

import org.example.dao.*;
import org.example.model.Employee;
import java.util.Scanner;

public class EmployeeService {

    EmployeeDAO empDao = new EmployeeDAO();
    LeaveDAO leaveDao = new LeaveDAO();
    PerformanceDAO perfDao = new PerformanceDAO();
    NotificationDAO notifDao = new NotificationDAO();
    GoalDAO goalDAO=new GoalDAO();

    public void menu(Employee emp) throws Exception {
        Scanner sc = new Scanner(System.in);
        while (true) {
            System.out.println("""
            1.View Profile
            2.Apply Leave
            3.View Leaves
            4.Submit Performance Review
            5.View Notifications
            6.Goals
            7.Update
            8.View Reporting Manager
            9.Leave Status
            10.Cancel Leave
            11.Holidays
            12.Set Goals
            0.Logout
            """);

            int ch = sc.nextInt();
            sc.nextLine();

            switch (ch) {
                case 1 -> empDao.viewProfile(emp.getId());
                case 2 -> leaveDao.applyLeave(emp.getId(), "2026-02-01", "2026-02-02", "CL", "Personal");
                case 3 -> leaveDao.viewLeaves(emp.getId());
                case 4 -> perfDao.submitReview(emp.getId(), "Tasks done", "Achievements", 4);
                case 5 -> notifDao.view(emp.getId());
                case 6 -> goalDAO.viewGoals(emp.getId());
                case 7 -> empDao.updateProfile(emp.getId(),emp.getPhone(),emp.getAddress(),emp.getEmergency());
                case 8 -> {
                    boolean found = empDao.viewReportingManager(emp.getId());
                    if (!found) {
                        System.out.println("❌ Reporting manager not assigned");
                    }
                }
                case 9 ->{
                    boolean found = empDao.viewAppliedLeaves(emp.getId());

                    if (!found) {
                        System.out.println("ℹ️ No leave applications found");
                    }
                }
                case 10 ->{
                    System.out.print("Enter Leave Application ID to cancel: ");
                    int leaveId = Integer.parseInt(sc.nextLine());
                    boolean success = empDao.cancelPendingLeave(emp.getId(), leaveId);
                    System.out.println(success
                            ? "✅ Leave application cancelled successfully"
                            : "❌ Unable to cancel (Only PENDING leaves can be cancelled)");
                }
                case 11 ->{
                    boolean found = empDao.viewHolidayCalendar();

                    if (!found) {
                        System.out.println("ℹ️ No holidays configured");
                    }
                }
                case 12 ->{

                    System.out.print("Goal Description: ");
                    String description = sc.nextLine();

                    System.out.print("Deadline (YYYY-MM-DD): ");
                    String deadline = sc.nextLine();

                    System.out.print("Priority: ");
                    String priority = sc.nextLine();

                    System.out.print("Success Metrics: ");
                    String metrics = sc.nextLine();

                    boolean success = empDao.addGoal(
                            emp.getId(), description, deadline, priority, metrics
                    );

                    System.out.println(success
                            ? "✅ Goal added successfully"
                            : "❌ Failed to add goal");
                }
                case 0 -> { return; }
            }
        }
    }
}
