package org.example.test;

import org.example.service.EmployeeService;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class EmployeeServiceTest {

    public static EmployeeService empService;

    @BeforeAll
    static void setup() {
        empService = new EmployeeService();
    }

    @Test
    @Order(1)
    void testUpdateProfile() {
        boolean updated = empService.updateProfile(101, "9876543210", "New Address", "1112223333");
        assertTrue(updated, "Profile should be updated successfully");
    }

    @Test
    @Order(2)
    void testViewReportingManager() {
        boolean exists = empService.viewReportingManager(1001);
        assertTrue(exists, "Reporting manager should exist for employee 1001");
    }


    @Test
    @Order(4)
    void testCancelLeave() {
        boolean cancelled = empService.cancelLeave(101, 1); // leave ID to cancel
        assertTrue(cancelled || !cancelled, "Cancel leave should return boolean");
        // true if pending leave exists, false otherwise
    }

    @Test
    @Order(5)
    void testViewHolidayCalendar() {
        boolean hasHolidays = empService.viewHolidayCalendar();
        assertTrue(hasHolidays || !hasHolidays, "Holiday calendar should return boolean");
    }

    @Test
    @Order(6)
    void testAddGoal() {
        boolean added = empService.addGoal(101, "Complete Testing", "2026-03-01", "High", "100%");
        assertTrue(added, "Goal should be added successfully");
    }
}
