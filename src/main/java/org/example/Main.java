package org.example;

import org.example.model.Employee;
import org.example.service.AuthService;
import org.example.util.MenuUtil;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws Exception {
        Scanner sc = new Scanner(System.in);
        AuthService authService = new AuthService();

        while (true) {
            System.out.println("\n=== REV WORKFORCE ===");
            System.out.print("Employee ID: ");
            int empId = sc.nextInt();
            sc.nextLine();

            System.out.print("Password: ");
            String password = sc.nextLine();

            Employee emp = authService.login(empId, password);

            if (emp != null) {
                MenuUtil.showMenu(emp);
            } else {
                System.out.println("Invalid credentials");
            }
        }
    }
}
