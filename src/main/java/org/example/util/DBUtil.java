package org.example.util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.DriverManager;

/**
 * DBUtil
 * -------
 * Utility class responsible for creating and providing
 * database connections for the application.
 */
public class DBUtil {

    // Logger instance for logging database-related errors
    private static final Logger logger =
            LogManager.getLogger(DBUtil.class);

    // Database connection URL (DB name: workforce)
    private static final String URL =
            "jdbc:mysql://localhost:3306/workforce";

    // Database username
    private static final String USER = "root";

    // Database password
    private static final String PASSWORD = "nysk2002";

    /**
     * Establishes and returns a connection to the database.
     *
     * @return Connection object if successful, otherwise null
     */
    public static Connection getConnection() {
        try {
            // Attempt to create and return a database connection
            return DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (Exception e) {
            // Log error instead of printing stack trace
            // This is safer and suitable for production environments
            logger.error("Database connection failed", e);
            return null;
        }
    }
}
