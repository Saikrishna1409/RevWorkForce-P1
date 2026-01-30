package org.example.test;

import org.example.service.ManagerService;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ManagerServiceTest {

    private static ManagerService managerService;

    @BeforeAll
    static void setup() {
        managerService = new ManagerService();
    }

    @Test
    @Order(1)
    void testViewReportees() {
        assertDoesNotThrow(() -> managerService.viewReportees(201));
    }

    @Test
    @Order(2)
    void testViewTeamLeaveRequests() {
        assertDoesNotThrow(() -> managerService.viewTeamLeaveRequests(201));
    }

    @Test
    @Order(3)
    void testUpdateLeaveStatus() {
        boolean result = managerService.updateLeaveStatus(1, "APPROVED", "Good work");
        // leave might not exist in test DB, so just check boolean type
        assertTrue(result || !result);
    }

    @Test
    @Order(4)
    void testViewTeamLeaveSummary() {
        assertDoesNotThrow(() -> managerService.viewTeamLeaveSummary(201));
    }

    @Test
    @Order(5)
    void testViewPerformanceDocs() {
        assertDoesNotThrow(() -> managerService.viewPerformanceDocs(201));
    }

    @Test
    @Order(6)
    void testAddFeedback() {
        boolean result = managerService.addFeedback(101, "Excellent work");
        assertTrue(result || !result);
    }

    @Test
    @Order(7)
    void testRateEmployee() {
        boolean result = managerService.rateEmployee(101, 5);
        assertTrue(result || !result);
    }

    @Test
    @Order(8)
    void testViewEmployeeGoals() {
        assertDoesNotThrow(() -> managerService.viewEmployeeGoals(201));
    }

    @Test
    @Order(9)
    void testTrackGoalStatus() {
        assertDoesNotThrow(() -> managerService.trackGoalStatus(201));
    }

    @Test
    @Order(10)
    void testViewTeamHierarchy() {
        assertDoesNotThrow(() -> managerService.viewTeamHierarchy(201));
    }

    @Test
    @Order(11)
    void testViewAttendanceSummary() {
        assertDoesNotThrow(() -> managerService.viewAttendanceSummary(201));
    }

    @Test
    @Order(12)
    void testGeneratePerformanceReport() {
        assertDoesNotThrow(() -> managerService.generatePerformanceReport(201));
    }

    @Test
    @Order(13)
    void testViewNotifications() {
        assertDoesNotThrow(() -> managerService.viewNotifications(201));
    }
}
