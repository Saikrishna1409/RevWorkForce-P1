package org.example.util;

import org.example.controller.AdminController;
import org.example.controller.EmployeeController;
import org.example.controller.ManagerController;
import org.example.model.Employee;


import org.example.controller.AdminController;
import org.example.controller.EmployeeController;
import org.example.controller.ManagerController;
import org.example.model.Employee;

// Utility class responsible for routing users to the correct menu
// based on their role after successful authentication
public class MenuUtil {

    // Displays the appropriate menu depending on the employee's role
    public static void showMenu(Employee emp) throws Exception {

        // Decide menu flow based on role stored in Employee object
        switch (emp.getRole()) {

            // If role is EMPLOYEE, show employee-specific menu
            case "EMPLOYEE" -> {
                EmployeeController employeeController = new EmployeeController();
                employeeController.menu(emp);
            }

            // If role is MANAGER, show manager-specific menu
            case "MANAGER" -> {
                ManagerController managerController = new ManagerController();
                managerController.menu(emp);
            }

            // If role is ADMIN, show admin-specific menu
            case "ADMIN" -> {
                AdminController adminController = new AdminController();
                adminController.menu();
            }

            // Handles any unexpected or invalid role values
            default -> System.out.println("❌ Invalid Role");
        }
    }
}
