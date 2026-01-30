package org.example.util;

import org.example.exception.ValidationException;

// Utility class that centralizes input validation logic
// Helps keep controllers and services clean and consistent
public class ValidationUtil {

    // Validates that the employee ID contains only digits
    // Throws ValidationException if non-numeric input is provided
    public static void validateEmpId(String input) {
        if (!input.matches("\\d+")) {
            throw new ValidationException("Employee ID must be numeric");
        }
    }

    // Validates password strength
    // Rules:
    // - Minimum 8 characters
    // - At least one letter
    // - At least one digit
    // - At least one special character
    public static void validatePassword(String password) {
        if (!password.matches("^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*#?&]).{8,}$")) {
            throw new ValidationException(
                    "Password must be min 8 chars, letters & numbers"
            );
        }
    }

    // Validates phone number format
    // Ensures exactly 10 digits (common mobile number format)
    public static void validatePhone(String phone) {
        if (!phone.matches("\\d{10}")) {
            throw new ValidationException("Invalid phone number");
        }
    }
}
