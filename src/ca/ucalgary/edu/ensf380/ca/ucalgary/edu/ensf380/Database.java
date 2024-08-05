package ca.ucalgary.edu.ensf380;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * This class handles the connection to the PostgreSQL database.
 * It provides a method to get a connection to the specified database.
 */
public class Database {
    private static final String URL = "jdbc:postgresql://localhost:5432/advertisements";
    private static final String USER = "oop";
    private static final String PASSWORD = "ucalgary";

    static {
        try {
            // Load the PostgreSQL JDBC driver class
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * Gets a connection to the PostgreSQL database.
     *
     * @return A Connection object to the PostgreSQL database.
     * @throws SQLException if a database access error occurs or the URL is null.
     */
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}
