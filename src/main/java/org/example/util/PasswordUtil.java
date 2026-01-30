package org.example.util;

import org.mindrot.jbcrypt.BCrypt;

// Utility class for handling password security operations
// Uses BCrypt for hashing and verifying passwords securely
public class PasswordUtil {

    // Hashes a plain-text password using BCrypt with a generated salt
    // This method should be used before storing passwords in the database
    public static String hash(String plainPassword) {
        return BCrypt.hashpw(plainPassword, BCrypt.gensalt());
    }

    // Verifies a plain-text password against a previously hashed password
    // Returns true if the password matches, false otherwise
    public static boolean verify(String plainPassword, String hashedPassword) {
        return BCrypt.checkpw(plainPassword, hashedPassword);
    }
}
