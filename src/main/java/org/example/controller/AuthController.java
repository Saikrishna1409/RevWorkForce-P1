package org.example.controller;

import org.example.model.Employee;
import org.example.service.AuthService;
import org.example.util.MenuUtil;
import org.example.util.ValidationUtil;

import java.util.Scanner;

public class AuthController {

    // Service layer to handle authentication logic
    private final AuthService authService = new AuthService();
    // Scanner to read input from console
    private final Scanner sc = new Scanner(System.in);

    /**
     * Handles employee login.
     * Prompts for Employee ID and Password, validates input, and calls AuthService.
     * Displays messages on success/failure and shows the main menu on successful login.
     *
     * @return Employee object if login successful, otherwise null
     * @throws Exception if validation fails
     */
    public Employee login() throws Exception {

        System.out.print("Employee ID: ");
        String empIdInput = sc.nextLine();
        // Validate employee ID format
        ValidationUtil.validateEmpId(empIdInput);
        int empId = Integer.parseInt(empIdInput);

        System.out.print("Password: ");
        String pwd = sc.nextLine();
        // Validate password format
        ValidationUtil.validatePassword(pwd);

        // Authenticate user
        Employee emp = authService.login(empId, pwd);

        if (emp == null) {
            System.out.println("❌ Invalid credentials or inactive account");
        } else {
            System.out.println("✅ Login successful. Welcome " + emp.getName());
            // Show the main menu according to employee role
            MenuUtil.showMenu(emp);
        }
        return emp;
    }

    /**
     * Allows an employee to change their own password.
     * Prompts for old password and new password, validates, and updates via AuthService.
     * Displays result of password change.
     *
     * @param emp currently logged-in employee
     */
    public void changePassword(Employee emp) {

        System.out.print("Old Password: ");
        String oldPwd = sc.nextLine();

        System.out.print("New Password: ");
        String newPwd = sc.nextLine();

        // Attempt password change
        boolean success = authService.changePassword(
                emp.getId(), oldPwd, newPwd
        );

        System.out.println(success
                ? "✅ Password changed successfully"
                : "❌ Old password incorrect");
    }

    /**
     * Allows an admin to reset an employee's password.
     * Prompts for employee ID and new password, and updates via AuthService.
     * Displays result of password reset.
     */
    public void resetPasswordByAdmin() {

        System.out.print("Employee ID: ");
        int empId = Integer.parseInt(sc.nextLine());

        System.out.print("New Password: ");
        String newPwd = sc.nextLine();

        // Reset password via AuthService
        boolean success = authService.resetPassword(empId, newPwd);

        System.out.println(success
                ? "✅ Password reset successfully"
                : "❌ Failed to reset password");
    }
}
