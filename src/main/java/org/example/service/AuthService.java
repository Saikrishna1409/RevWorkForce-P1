package org.example.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.DAO.AuthDAO;
import org.example.exception.BusinessException;
import org.example.model.Employee;
import java.sql.SQLException;

// Service layer responsible for authentication-related business logic
// Acts as an intermediary between Controller and AuthDAO
public class AuthService {
    private static final Logger logger =
            LogManager.getLogger(AuthService.class);

    // DAO object used to interact with authentication-related database operations
    private final AuthDAO authDAO = new AuthDAO();

    // Handles employee login logic
    // Validates credentials and ensures the employee account is active
    public Employee login(int empid, String password) {
        logger.info("Login attempt for empId={}", empid);
        try {
            // Fetch active employee matching given credentials
            Employee emp = authDAO.findActiveEmployee(empid, password);

            // If no employee is found, treat it as invalid login
            if (emp == null) {
                logger.warn("Invalid login attempt for empId={}", empid);
                throw new BusinessException("Invalid credentials or inactive account");
            }

            // Successful login returns Employee object
            return emp;


        } catch (SQLException e) {
            logger.error("Error during login for empId={}", empid, e);
            // Converts low-level SQL exception into a business-friendly exception
            throw new BusinessException("System error. Please try again later");
        }

    }

    // Allows employee to change password after validating old password
    public boolean changePassword(int empId, String oldPwd, String newPwd) {

        // Verifies whether the old password matches the stored password
        boolean valid = authDAO.validateOldPassword(empId, oldPwd);

        // If old password is incorrect, do not proceed
        if (!valid) {
            return false;
        }

        // Updates password with new value
        return authDAO.updatePassword(empId, newPwd);
    }

    // Resets password directly (used by admin or system-level operation)
    public boolean resetPassword(int empId, String newPwd) {

        // Updates password without validating old password
        return authDAO.updatePassword(empId, newPwd);
    }
}

