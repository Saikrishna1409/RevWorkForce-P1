package org.example.test;

import org.example.service.AdminService;
import org.junit.jupiter.api.*;

import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class AdminServiceTest {

    private static AdminService adminService;

    @BeforeAll
    static void setup() {
        adminService = new AdminService();
    }


    @Test
    @Order(2)
    void testUpdateEmployee() {
        boolean updated = adminService.updateEmployee(101, "john_new@example.com", "Senior Developer");
        assertTrue(updated, "Employee should be updated successfully");
    }

    @Test
    @Order(3)
    void testUpdateStatus() {
        boolean updated = adminService.updateStatus(101, true);
        assertTrue(updated, "Employee status should be updated successfully");
    }

    @Test
    @Order(4)
    void testAssignManager() {
        boolean assigned = adminService.assignManager(101, 100);
        assertTrue(assigned, "Manager should be assigned successfully");
    }

    @Test
    @Order(5)
    void testViewEmployees() {
        // Use a dummy Scanner since the method needs it
        Scanner sc = new Scanner(System.in);
        String employees = adminService.viewEmployees(sc);
        assertNotNull(employees, "Should return employee list");
        assertTrue(employees.contains("John Doe"), "Employee list should contain John Doe");
    }

    @Test
    @Order(6)
    void testAddDepartment() {
        boolean added = adminService.addDepartment("IT");
        assertTrue(added, "Department should be added successfully");
    }


    @AfterAll
    static void cleanup() {
        // Optional: remove test data if needed
    }
}
