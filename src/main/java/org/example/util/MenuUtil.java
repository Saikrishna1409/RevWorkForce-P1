package org.example.util;

import org.example.model.Employee;
import org.example.service.*;

public class MenuUtil {

    public static void showMenu(Employee emp) throws Exception {
        switch (emp.getRole()) {
            case "EMPLOYEE" -> new EmployeeService().menu(emp);
            case "MANAGER" -> new ManagerService().menu(emp);
            case "ADMIN" -> new AdminService().menu();
        }
    }
}
