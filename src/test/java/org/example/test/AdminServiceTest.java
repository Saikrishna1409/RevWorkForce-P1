package org.example.test;

import org.example.dao.EmployeeDAO;
import org.example.service.AuthService;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class AdminServiceTest {

        EmployeeDAO dao = new EmployeeDAO();

        @Test
        void testResetPassword() {
            AuthService auth = new AuthService();
            assertDoesNotThrow(() -> auth.resetPassword(999, "newPassword123"));
        }
    }
