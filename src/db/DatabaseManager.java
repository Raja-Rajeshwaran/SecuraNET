package db;

import java.sql.*;

public class DatabaseManager {
    private static final String URL = "jdbc:mysql://localhost:3306/securanet";
    private static final String USER = "root";
    private static final String PASSWORD;
    static {
        String envPassword = System.getenv("APP_PASSWORD");
        if (envPassword == null) {
            throw new RuntimeException("Password environment variable APP_PASSWORD not set!");
        }
        PASSWORD = envPassword;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            System.err.println("MySQL JDBC Driver not found.");
        }
    }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}
