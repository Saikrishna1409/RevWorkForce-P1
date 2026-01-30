package org.example.test;

import org.example.model.Employee;
import org.example.service.AuthService;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class AuthServiceTest {

    private static AuthService authService;

    @BeforeAll
    static void setup() {
        authService = new AuthService();
    }

    @Test
    @Order(1)
    void testLoginSuccess() {
        Employee emp = authService.login(1001, "rahul@123"); // password used during addEmployee
        assertNotNull(emp, "Login should succeed for correct credentials");
    }

    @Test
    @Order(2)
    void testLoginFailure() {
        assertThrows(Exception.class, () -> authService.login(101, "wrongPassword"));
    }

    @Test
    @Order(3)
    void testChangePasswordSuccess() {
        boolean changed = authService.changePassword(101, "initialPassword", "newSecret123");
        assertTrue(changed, "Password should change successfully");
    }

    @Test
    @Order(4)
    void testChangePasswordFailure() {
        boolean changed = authService.changePassword(101, "wrongOldPwd", "newPassword");
        assertFalse(changed, "Password change should fail for wrong old password");
    }

    @Test
    @Order(5)
    void testResetPassword() {
        boolean reset = authService.resetPassword(101, "reset123");
        assertTrue(reset, "Admin password reset should succeed");
    }
}
