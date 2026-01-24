package org.example.service;

import org.example.dao.*;

import java.util.Scanner;

import static com.mysql.cj.conf.PropertyKey.logger;

public class AdminService {

    private final DepartmentDao departmentDao = new DepartmentDao();
    EmployeeDAO empDao = new EmployeeDAO();
    Scanner sc = new Scanner(System.in);
    AuthService authService =new AuthService();
    LeaveDAO leaDao=new LeaveDAO();
    private final PerformanceDAO performanceDao = new PerformanceDAO();
    private final AuditDao auditDao = new AuditDao();

    public void menu() {
        Scanner sc = new Scanner(System.in);
        int choice;

        do {
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
            switch (choice) {
                case 1 -> {

                    System.out.print("Email: ");
                    String email = sc.nextLine();
                    System.out.println();

                    System.out.print("emp-id: ");
                    String emp_id = sc.nextLine();

                    System.out.print("Name: ");
                    String name = sc.nextLine();

                    System.out.print("Designation: ");
                    String designation = sc.nextLine();

                    System.out.print("Manager ID (0 if none): ");
                    int managerId = sc.nextInt();

                    boolean success = empDao.addEmployee(Integer.parseInt(emp_id),
                            name, email, designation, managerId
                    );

                    System.out.println(success ? "✅ Employee added" : "❌ Failed to add employee");
                }

                case 2 -> {
                    System.out.print("Employee ID: ");
                    int empId = sc.nextInt();
                    sc.nextLine();

                    System.out.print("New Email: ");
                    String email = sc.nextLine();

                    System.out.print("New Designation: ");
                    String designation = sc.nextLine();

                    boolean success = empDao.updateEmployee(empId, email, designation);
                    System.out.println(success ? "✅ Employee updated" : "❌ Update failed");
                }

                case 3 -> {
                    System.out.println("""
                            Search By:
                            1. View All
                            2. By ID
                            3. By Name
                            """);
                    int uchoice = sc.nextInt();
                    sc.nextLine();

                    switch (uchoice) {
                        case 1 -> empDao.viewAllEmployees();
                        case 2 -> {
                            System.out.print("Employee ID: ");
                            int id = sc.nextInt();
                            empDao.searchById(id);
                        }
                        case 3 -> {
                            System.out.print("Name: ");
                            String name = sc.nextLine();
                            empDao.searchByName(name);
                        }
                        default -> System.out.println("❌ Invalid choice");
                    }
                }

                case 4 -> {
                    System.out.print("Employee ID: ");
                    int empId = sc.nextInt();

                    System.out.print("Activate? (true/false): ");
                    boolean active = sc.nextBoolean();

                    boolean success = empDao.updateStatus(empId, active);
                    System.out.println(success ? "✅ Status updated" : "❌ Status update failed");
                }

                case 5 -> resetEmployeePassword(sc);
                case 6 -> {
                    System.out.print("Employee ID: ");
                    int empId = Integer.parseInt(sc.nextLine());

                    System.out.print("Manager ID: ");
                    int managerId = Integer.parseInt(sc.nextLine());

                    boolean success = empDao.assignManager(empId, managerId);

                    System.out.println(success
                            ? "✅ Reporting manager assigned successfully"
                            : "❌ Failed to assign manager");
                }

                case 7 -> {
                    System.out.print("Leave Type (CL / SL / PL): ");
                    String type = sc.nextLine().toUpperCase();

                    System.out.print("Annual Quota: ");
                    int quota = Integer.parseInt(sc.nextLine());

                    boolean success = leaDao.addOrUpdateLeaveType(type, quota);

                    System.out.println(success
                            ? "✅ Leave type configured"
                            : "❌ Failed to configure leave type");
                }

                case 8 -> {
                    System.out.print("Employee ID: ");
                    int empId = Integer.parseInt(sc.nextLine());

                    System.out.print("Leave Type (CL / SL / PL): ");
                    String type = sc.nextLine().toUpperCase();

                    System.out.print("Days (+ / -): ");
                    int days = Integer.parseInt(sc.nextLine());

                    boolean success = leaDao.adjustLeaveBalance(empId, type, days);

                    System.out.println(success
                            ? "✅ Leave balance adjusted"
                            : "❌ Failed to adjust leave balance");
                }
                case 9 -> {
                    System.out.print("Holiday Date (YYYY-MM-DD): ");
                    String date = sc.nextLine();

                    System.out.print("Holiday Name: ");
                    String name = sc.nextLine();

                    boolean success = leaDao.addHoliday(date, name);

                    System.out.println(success
                            ? "✅ Holiday added"
                            : "❌ Failed to add holiday");

                }
                case 10 -> {
                    System.out.println("""
                            1. Add Department
                            2. Add Designation
                            3. View Departments
                            """);

                    int d = Integer.parseInt(sc.nextLine());

                    switch (d) {
                        case 1 ->{
                            System.out.print("Department Name: ");
                            String name = sc.nextLine();

                            boolean success = departmentDao.addDepartment(name);
                            System.out.println(success ? "✅ Department added" : "❌ Failed to add department");

                        }
                        case 2 -> {
                                System.out.print("Department ID: ");
                                int deptId = Integer.parseInt(sc.nextLine());

                                System.out.print("Designation Name: ");
                                String designation = sc.nextLine();

                                boolean success = departmentDao.addDesignation(deptId, designation);
                                System.out.println(success ? "✅ Designation added" : "❌ Failed to add designation");

                        }
                        case 3 -> departmentDao.viewDepartments();
                        default -> System.out.println("❌ Invalid choice");
                    }


                }

                case 11 -> {
                        System.out.print("Review Cycle Name (e.g. Q1-2026): ");
                        String name = sc.nextLine();

                        System.out.print("Start Date (YYYY-MM-DD): ");
                        String start = sc.nextLine();

                        System.out.print("End Date (YYYY-MM-DD): ");
                        String end = sc.nextLine();

                        boolean success = performanceDao.addReviewCycle(name, start, end);

                        System.out.println(success
                                ? "✅ Performance review cycle added"
                                : "❌ Failed to add review cycle");
                }
                case 12 -> {
                        auditDao.viewLogs();
                }

                case 0 -> System.out.println("🔒 Logged out successfully");
                default -> System.out.println("❌ Invalid choice");
            }

        } while (choice != 0);
    }

    private void resetEmployeePassword(Scanner sc) {
        System.out.print("Employee ID: ");
        int id = sc.nextInt();
        System.out.print("New Password: ");
        String pwd = sc.next();
        authService.resetPassword(id, pwd);
        System.out.println("✅ Password reset successfully");
    }
    }

